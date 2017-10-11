/*****************************************************************************
 * DESC       ：法定第三者XX确认脚本控制
* Author     : X
 * CREATEDATE ：2003-02-12
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/


function newProposal(editType)
{
  var strUrl = "/prpall/indiv/sh/UIProposalCheck.jsp"
             + "?EDITTYPE=" + editType
             + "&BIZTYPE="  + fm.BizType.value
             + "&BizNo="    + ""
             + "&DataNo="   + ""
             + "&ValidNo="  + fm.ValidNo.value;
  window.location = strUrl;
}


function updateProposal(editType)
{
  if(trim(fm.CertiNo.value)=="")
  {
    errorMessage("请输入要进行修改的XX单的号码!");
    fm.CertiNo.focus();
    return;
  }
  
  var strUrl = "/prpall/indiv/sh/UIProposalCheck.jsp"
             + "?EDITTYPE=" + editType
             + "&BIZTYPE="  + fm.BizType.value
             + "&BizNo="    + ""
             + "&DataNo="   + fm.CertiNo.value
             + "&ValidNo="  + fm.ValidNo.value;
  window.location = strUrl;
}


function copyProposal(editType)
{
  if(trim(fm.CertiNo.value)=="")
  {
    errorMessage("请输入被复制的XX单的号码!");
    fm.CertiNo.focus();
    return;
  }

  var strUrl = "/prpall/indiv/sh/UIProposalCheck.jsp"
             + "?EDITTYPE=" + editType
             + "&BIZTYPE="  + fm.BizType.value
             + "&BizNo="    + ""
             + "&DataNo="   + fm.CertiNo.value
             + "&ValidNo="  + fm.ValidNo.value;
  window.location = strUrl;
}


function renewalProposal(editType)
{
  if(trim(fm.CertiNo.value)=="")
  {
    errorMessage("请输入被续XXXXX的XX的号码!");
    fm.CertiNo.focus();
    return;
  }

  var strUrl = "/prpall/indiv/sh/UIProposalCheck.jsp"
             + "?EDITTYPE=" + editType
             + "&BIZTYPE="  + fm.BizType.value
             + "&BizNo="    + ""
             + "&DataNo="   + fm.CertiNo.value
             + "&ValidNo="  + fm.ValidNo.value;             
  window.location = strUrl;
}