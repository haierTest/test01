package com.sp.prpall.solr;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.taskmng.util.TaskMngUtil;

public class SolrCreateIndexTask extends TimerTask {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    /**
     * ��ʱ��������,���������������ݲ����������ݿ� add by cuiyan 20130509
     */
    public void run() {
        SolrCreateIndexPre solrCreateIndexPre = new SolrCreateIndexPre();
        
        logger.debug("ͬ��XXXXX��Ϣ����begin");
        try {
            solrCreateIndexPre.syncCostomerData();
        }catch (Exception e) {
            logger.error("ͬ��XXXXX��Ϣ�����쳣", e);
            e.printStackTrace();
            TaskMngUtil.insertMidDataLog("SolrCreateIndexTask.run", e.getMessage(), e, TaskMngUtil.SolrIndexCons.SolrIndex_SolrCreateIndexTask_JobName);
        }
        logger.debug("ͬ��XXXXX��Ϣ����end");
        logger.debug("ͬ��������Ϣ����begin");
        try {
            solrCreateIndexPre.syncModelData();
        }catch (Exception e) {
            logger.error("ͬ��������Ϣ�����쳣", e);
            e.printStackTrace();
            TaskMngUtil.insertMidDataLog("SolrCreateIndexTask.run", e.getMessage(), e, TaskMngUtil.SolrIndexCons.SolrIndex_SolrCreateIndexTask_JobName);
        }
        logger.debug("ͬ��������Ϣ����end");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        
        
        logger.debug("XXXXX��Ϣ������������begin");
        try{
        
            solrCreateIndexPre.createCustomerIndex();
        
        }catch (Exception e) {
            logger.error("XXXXX��Ϣ���������Ƿ����쳣", e);
            e.printStackTrace();
            TaskMngUtil.insertMidDataLog("SolrCreateIndexTask.run", e.getMessage(), e, TaskMngUtil.SolrIndexCons.SolrIndex_SolrCreateIndexTask_JobName);
        }
        logger.debug("XXXXX��Ϣ������������end");
        
        logger.debug("������Ϣ������������begin");
        try{
       
            solrCreateIndexPre.createModelIndex();
        }catch (Exception e) {
            logger.error("������Ϣ�������������쳣", e);
            e.printStackTrace();
            TaskMngUtil.insertMidDataLog("SolrCreateIndexTask.run", e.getMessage(), e, TaskMngUtil.SolrIndexCons.SolrIndex_SolrCreateIndexTask_JobName);
        }
        logger.debug("������Ϣ������������end");

    }

}
