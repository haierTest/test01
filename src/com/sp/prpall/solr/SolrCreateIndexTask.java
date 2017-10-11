package com.sp.prpall.solr;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.taskmng.util.TaskMngUtil;

public class SolrCreateIndexTask extends TimerTask {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    /**
     * 定时生成索引,将生成索引的数据插入索引数据库 add by cuiyan 20130509
     */
    public void run() {
        SolrCreateIndexPre solrCreateIndexPre = new SolrCreateIndexPre();
        
        logger.debug("同步XXXXX信息表——begin");
        try {
            solrCreateIndexPre.syncCostomerData();
        }catch (Exception e) {
            logger.error("同步XXXXX信息表发生异常", e);
            e.printStackTrace();
            TaskMngUtil.insertMidDataLog("SolrCreateIndexTask.run", e.getMessage(), e, TaskMngUtil.SolrIndexCons.SolrIndex_SolrCreateIndexTask_JobName);
        }
        logger.debug("同步XXXXX信息表——end");
        logger.debug("同步车辆信息表——begin");
        try {
            solrCreateIndexPre.syncModelData();
        }catch (Exception e) {
            logger.error("同步车辆信息表发生异常", e);
            e.printStackTrace();
            TaskMngUtil.insertMidDataLog("SolrCreateIndexTask.run", e.getMessage(), e, TaskMngUtil.SolrIndexCons.SolrIndex_SolrCreateIndexTask_JobName);
        }
        logger.debug("同步车辆信息表——end");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        
        
        logger.debug("XXXXX信息创建索引——begin");
        try{
        
            solrCreateIndexPre.createCustomerIndex();
        
        }catch (Exception e) {
            logger.error("XXXXX信息创建索引是发生异常", e);
            e.printStackTrace();
            TaskMngUtil.insertMidDataLog("SolrCreateIndexTask.run", e.getMessage(), e, TaskMngUtil.SolrIndexCons.SolrIndex_SolrCreateIndexTask_JobName);
        }
        logger.debug("XXXXX信息创建索引——end");
        
        logger.debug("车辆信息创建索引——begin");
        try{
       
            solrCreateIndexPre.createModelIndex();
        }catch (Exception e) {
            logger.error("车辆信息创建索引发生异常", e);
            e.printStackTrace();
            TaskMngUtil.insertMidDataLog("SolrCreateIndexTask.run", e.getMessage(), e, TaskMngUtil.SolrIndexCons.SolrIndex_SolrCreateIndexTask_JobName);
        }
        logger.debug("车辆信息创建索引——end");

    }

}
