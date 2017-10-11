package com.sp.prpall.solr;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import com.sp.prpall.solr.db.DBCustomerIndex;
import com.sp.prpall.solr.db.DBModelIndex;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

/**
 * 生成索引业务处理
 * 
 * @author cuiyan 20130508
 * 
 */
public class SolrCreateIndexPre {

    private SolrServer server;
    /**
     * 定时将t_spis_customer_unit 表中的CUSTOMERCNAME同步到t_spis_customer_unit_sync表中 
     * add by cuiyan 20130523
     */
    public void syncCostomerData(){
        DbPool dbPool = new DbPool();
        DBCustomerIndex dbcustomer = new DBCustomerIndex();
        try {
            
            if("1".equals(SysConfig.getProperty("CUSTOMER_DATA_SOURCE_SWITCH"))){
                dbPool.open(SysConfig.CONST_CUSTOMERDATASOURCE);
            }else{
            dbPool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            
            int notSameCount = dbcustomer.compareCoustomerCount(dbPool);
            if(notSameCount == 0){
                return;
            }else{
                dbcustomer.insertCoustomerSync(dbPool);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 定时将prpdcarmodel 表中的CUSTOMERCNAME同步到prpdcarmodel_sync表中 
     * add by cuiyan 20130523
     */
    public void syncModelData(){
        DbPool dbPool = new DbPool();
        DBModelIndex dbmodel = new DBModelIndex();
        try {
        	
        	if ("1".equals(SysConfig.getProperty("DB_SPLIT_SWITCH"))) {
                dbPool.open("platformNewDataSource");        		
        	} else {
                dbPool.open(SysConfig.CONST_DDCCDATASOURCE);        		
        	}
            
            int notSameCount = dbmodel.compareModelCount(dbPool);
            if(notSameCount == 0){
                return;
            }else{
                dbmodel.insertModelSync(dbPool);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * XXXXX信息名称建立索引 ,插入t_spis_customer_unit_index表 add by cuiyan 20130508
     */
    public void createCustomerIndex() {
        List list = new ArrayList();
        DbPool dbPool = new DbPool();
        DBCustomerIndex dbcustomer = new DBCustomerIndex();
        ResultSet resultSet = null;
        try {
            
            if("1".equals(SysConfig.getProperty("CUSTOMER_DATA_SOURCE_SWITCH"))){
                dbPool.open(SysConfig.CONST_CUSTOMERDATASOURCE);
            }else{
            
            dbPool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            int notSameCount = dbcustomer.getNotNameCount(dbPool);
            
            if (notSameCount == 0) {
                return;
            } else {
                server = new CommonsHttpSolrServer(AppConfig.get("sysconst.SOLR_CUSTOMER_CREATE_URL"));
                resultSet = dbcustomer.queryDifferefentNames(dbPool);
            }
            dbcustomer.insertBatchPre(dbPool);
           
            while (resultSet.next()) {
                String customerCname = resultSet.getString("CUSTOMERCNAME");
                if (customerCname == null || "".equals(customerCname))
                    continue;
                String id = change2Hashcode(customerCname);
                SolrInputDocument doc = new SolrInputDocument();
                doc.addField("id", id);
                doc.addField("customerCName", customerCname);
                list.add(doc);
                dbcustomer.insertBatch(dbPool, id, customerCname);
            }
            server.add(list);
            server.commit();
            dbcustomer.insertBatchLast(dbPool);
            dbPool.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbPool.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            server = null;
            System.runFinalization();
            System.gc();
        }
    }

    /**
     * 车辆信息名称建立索引add by cuiyan 20130508
     */
    public void createModelIndex() {
        List list = new ArrayList();
        

        DbPool dbPool = new DbPool();
        DBModelIndex dbmodel = new DBModelIndex();
        ResultSet resultSet = null;
        try {
        	
        	if ("1".equals(SysConfig.getProperty("DB_SPLIT_SWITCH"))) {
                dbPool.open("platformNewDataSource");        		
        	} else {
                dbPool.open(SysConfig.CONST_DDCCDATASOURCE);        		
        	}
            

            int notSameCount = dbmodel.getNotSameCount(dbPool);
            
            
            
            
            if (notSameCount == 0) {
                return;
            } else {
                server = new CommonsHttpSolrServer(AppConfig.get("sysconst.SOLR_MODEL_CREATE_URL"));
                resultSet = dbmodel.queryDifferefentNames(dbPool);
            }

            
            dbmodel.insertBatchPre(dbPool);
          
            while (resultSet.next()) {
                String modelCname = resultSet.getString("MODELNAME");
                if (modelCname == null || "".equals(modelCname))
                    continue;
                String id = change2Hashcode(modelCname);
                SolrInputDocument doc = new SolrInputDocument();
                doc.addField("id", id);
                doc.addField("modelCName", modelCname);
                list.add(doc);
                dbmodel.insertBatch(dbPool, id, modelCname);
            }
            server.add(list);
            server.commit();
            dbmodel.insertBatchLast(dbPool);
            dbPool.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbPool.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            server = null;
            System.runFinalization();
            System.gc();
        }
    }

    /**
     * 将汉字转化成字符串
     * 
     * @param plainText
     *            要转化的汉字
     * @return 32位hashcode编码 add by cuiyan 20130508
     */
    public static String change2Hashcode(String plainText) {
        StringBuffer buf = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return buf.toString();
    }

}
