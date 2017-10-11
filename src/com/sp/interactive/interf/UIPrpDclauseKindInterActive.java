package com.sp.interactive.interf;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.Vector;

import com.sp.prpall.ui.UIKindInfo;
import com.sp.utiall.blsvr.BLPrpDclauseKind;
import com.sp.utiall.blsvr.BLPrpDkind;
import com.sp.utiall.schema.PrpDclauseKindSchema;
import com.sp.utiall.schema.PrpDkindSchema;
import com.sp.utility.error.UserException;

/**
 * 中海需求 根据传来的信息来处理主XXXXX与附加XXXXX的关系
 * @author qilin
 * */

public class UIPrpDclauseKindInterActive {
	  /** 初始化标志 */
	  private static boolean isInited = false;
	  /** 条款XXXXX别关系信息 */
	  private static BLPrpDclauseKind blPrpDclauseKind = null;
	  /** XXXXX别信息 */
	  private static TreeMap dkindTreeMap = null;

	  /**
	   * 判断是否已被初始化
	   * @return 是否已被初始化
	   */
	  public static boolean isInited()
	  {
	    return isInited;
	  }

	  /**
	   * 初始化条款XXXXX别关系
	   * @param forceInit 强制初始化标志
	   * @throws Exception
	   */
	  public static synchronized void init(boolean forceInit) throws Exception
	  {
	    
	    BLPrpDkind blPrpDkind = null;
	    
	    String[]  arrRiskCode = {"0507","0528","0508","0509","0510","0511","0512","0513","0516","0517","0518","0515","0521"};
	    
	    int i = 0;

	    
	    if(forceInit==false && isInited==true)
	    {
	      return;
	    }

	    PrpDclauseKindSchema  prpDclauseKindSchema = null;
	    PrpDkindSchema        prpDkindSchema       = null;
	    BLPrpDclauseKind      blPrpDclauseKindTem  = new BLPrpDclauseKind();
	    BLPrpDkind            blPrpDkindTem        = new BLPrpDkind();
	    blPrpDclauseKind = new BLPrpDclauseKind();
	    dkindTreeMap = new TreeMap();
	    blPrpDkind = new BLPrpDkind();

	    for(i=0;i<arrRiskCode.length;i++)
	    {
	      
	      blPrpDclauseKindTem.queryByRiskCode(arrRiskCode[i]);
	      for(int j=0;j<blPrpDclauseKindTem.getSize();j++)
	      {
	      	prpDclauseKindSchema = new PrpDclauseKindSchema();
	        prpDclauseKindSchema = blPrpDclauseKindTem.getArr(j);
	        blPrpDclauseKind.setArr(prpDclauseKindSchema);
	      }

	      
	      blPrpDkindTem.query("RiskCode='"+arrRiskCode[i]+"' AND ValidStatus='1' ORDER BY KindCode",0);
	      for(int j=0;j<blPrpDkindTem.getSize();j++)
	      {
	      	prpDkindSchema = new PrpDkindSchema();
	        prpDkindSchema = blPrpDkindTem.getArr(j);
	        blPrpDkind.setArr(prpDkindSchema);
	      }
	    }

	    for(i=0; i<blPrpDkind.getSize(); i++)
	    {
	    	dkindTreeMap.put(blPrpDkind.getArr(i).getKindCode(),blPrpDkind.getArr(i));
	    }

	    
	    isInited = true;
	  }

	  /**
	   * 根据条款获取主XXXXX信息
	   * @param iClauseType 条款代码
	   * @throws Exception
	   */
	  public static UIKindInfo[] queryKindMain(String iClauseType)
	    throws UserException,Exception
	  {
	    
	    if(isInited==false)
	    {
	      throw new UserException(-98,-999,"UIPrpDclauseKind.queryKindMain","UIPrpDclauseKind类没有初始化!");
	    }
	    
	    int i = 0;
	    String strClauseType = "";
	    String strKindCode = "";
	    Vector vector = new Vector();
	    HashSet hashSet = new HashSet();
	    UIKindInfo uiKindInfo = null;
	    String strCalculateFlag = "";
	    PrpDkindSchema prpDkindSchema = null;

	    
	    if(iClauseType.equals("F34") || iClauseType.equals("F35"))
	    {
	      hashSet.add("A");
	      uiKindInfo = new UIKindInfo( (PrpDkindSchema)dkindTreeMap.get("A") );
	      vector.add( uiKindInfo );
	    }

	    
	    for(i=0; i<blPrpDclauseKind.getSize(); i++)
	    {
	      strClauseType = blPrpDclauseKind.getArr(i).getClauseType();
	      strKindCode   = blPrpDclauseKind.getArr(i).getKindCode();
	      
	      if(!strClauseType.equals(iClauseType))
	    	continue;
	      
	      
	      if(iClauseType.equals("F22") && strKindCode.equals("A"))
	        continue;
	      
	      if((iClauseType.equals("F54") ||
	    	  iClauseType.equals("F55") ||
	    	  iClauseType.equals("F56") ||
	          
	          iClauseType.equals("F58") ||
	          iClauseType.equals("F59") ||
	    	  iClauseType.equals("F61") ||    	  
	    	  iClauseType.equals("F57")) && 
	    	  (strKindCode.equals("D1") ||
	    	   strKindCode.equals("G")))
		     continue;
	      if((iClauseType.equals("F44") ||
	          iClauseType.equals("F45") ||
	          iClauseType.equals("F46") ||
	          iClauseType.equals("F51") ||          
	          iClauseType.equals("F47")) && 
	          (strKindCode.equals("D3") ||
	           strKindCode.equals("D4") ||
	           strKindCode.equals("G1")))
	    	 continue;
	      

	      
	      if(strClauseType.equals(iClauseType))
	      {
	        prpDkindSchema = (PrpDkindSchema)dkindTreeMap.get(strKindCode);
	        if(prpDkindSchema == null) continue;
	        
	        strCalculateFlag = prpDkindSchema.getCalculateFlag();
	        if(strCalculateFlag.length()  >= 3 	 &&
	           strCalculateFlag.charAt(2) == '1' &&
	           hashSet.add(strKindCode))
	        {
	          uiKindInfo = new UIKindInfo( prpDkindSchema );
	          vector.add( uiKindInfo );
	        }
	      }
	    }
	    return (UIKindInfo[])vector.toArray(new UIKindInfo[vector.size()]);

	  }

	  /**
	   * 根据条款获取附加XXXXX信息
	   * @param iClauseType 条款代码
	   * @param iArrKindCode 主XXXXX代码数组
	   * @throws Exception
	   */
	  public static UIKindInfo[] queryKindSub(String iClauseType, String[] iArrKindCode)
	    throws UserException,Exception
	  {
	    
	    if(isInited==false)
	    {
	      throw new UserException(-98,-999,"UIPrpDclauseKind.queryKindSub","UIPrpDclauseKind类没有初始化!");
	    }
	    
	    int i = 0;
	    String strClauseType = "";
	    String strKindCodeMain = "";
	    String strKindCodeSub = "";
	    TreeMap treeMap = new TreeMap();
	    HashSet hashSet = new HashSet();
	    UIKindInfo uiKindInfo = null;

	    
	    for(i=0; i<iArrKindCode.length; i++) hashSet.add(iArrKindCode[i]);

	    
	    for(i=0; i<blPrpDclauseKind.getSize(); i++)
	    {
	      strClauseType   = blPrpDclauseKind.getArr(i).getClauseType();
	      
	      if(strClauseType.equals(iClauseType))
	      {
	        strKindCodeMain = blPrpDclauseKind.getArr(i).getKindCode();
	        strKindCodeSub  = blPrpDclauseKind.getArr(i).getRelateKindCode();

	        
	        if(hashSet.contains(strKindCodeMain) && !treeMap.containsKey(strKindCodeSub))
	        {
	          uiKindInfo = new UIKindInfo( (PrpDkindSchema)dkindTreeMap.get(strKindCodeSub) );
	          uiKindInfo.setAttachFlag( strKindCodeMain );
	          treeMap.put(strKindCodeSub,uiKindInfo);
	        }
	        else 
	        {
	          uiKindInfo = (UIKindInfo )treeMap.get(strKindCodeSub);
	          if(uiKindInfo!=null)
	          {
	            uiKindInfo.setAttachFlag(uiKindInfo.getAttachFlag()+","+strKindCodeMain);
	          }
	        }
	      }
	    }

	    return (UIKindInfo[])treeMap.values().toArray(new UIKindInfo[treeMap.size()]);
	  }

	  /**
	   * 根据条款、附加XXXXX获取归属主XXXXX
	   * @param iClauseType 条款代码
	   * @param iKindCode 附加XXXXX代码
	   * @throws Exception
	   */
	  public static String getAttachCode(String iClauseType, String iKindCode)
	    throws UserException,Exception
	  {
	    
	    if(isInited==false)
	    {
	      throw new UserException(-98,-999,"UIPrpDclauseKind.getAttachCode","UIPrpDclauseKind类没有初始化!");
	    }
	    
	    int i = 0;
	    String strReturn = "";
	    String strClauseType = "";
	    String strKindCodeMain = "";
	    String strKindCodeSub = "";

	    
	    for(i=0; i<blPrpDclauseKind.getSize(); i++)
	    {
	      strClauseType   = blPrpDclauseKind.getArr(i).getClauseType();
	      strKindCodeSub  = blPrpDclauseKind.getArr(i).getRelateKindCode();
	      
	      if(strClauseType.equals(iClauseType) && strKindCodeSub.equals(iKindCode))
	      {
	        strReturn = strReturn + "," + blPrpDclauseKind.getArr(i).getKindCode();
	      }
	    }

	    
	    if(strReturn.length()>0)
	    {
	      strReturn = strReturn.substring(1);
	    }
	    return strReturn;
	  }

}
