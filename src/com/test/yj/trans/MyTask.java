package com.test.yj.trans;

import java.util.TimerTask;

import com.sp.taskmng.util.TaskMngUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class MyTask  extends TimerTask{
    
	protected final Log logger = LogFactory.getLog(getClass());
    
    public void run() {
        try{
        	





        	
        		com.test.huaan.dto.HABFMyTask bfhmt = new com.test.huaan.dto.HABFMyTask();
        		bfhmt.runTrans(); 
        		com.test.huaan.dto.HAMyTask hmt1 = new com.test.huaan.dto.HAMyTask();        
        		hmt1.runhlTrans();
        		

        		
        	

        	
            
        	logger.info("selert xian cheng");
        	
        }catch(Exception e){
            e.printStackTrace();
            
        	logger.error(e.toString());
            
            
            TaskMngUtil.insertMidDataLog("MyTask.run", e.getMessage(), e, TaskMngUtil.TransCons.Trans_MyTask_JobName);
        }
    }
}