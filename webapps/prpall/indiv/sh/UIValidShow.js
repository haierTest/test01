/*****************************************************************************
 * DESC       ������������XXȷ�Ͻű�����
* Author     : X
 * CREATEDATE ��2003-02-12
 * MODIFYLIST ��   Name       Date            Reason/Contents
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
    errorMessage("������Ҫ�����޸ĵ�XX���ĺ���!");
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
    errorMessage("�����뱻���Ƶ�XX���ĺ���!");
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
    errorMessage("�����뱻��XXXXX��XX�ĺ���!");
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