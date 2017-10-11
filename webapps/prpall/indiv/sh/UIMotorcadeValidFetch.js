/*
****************************************************************************
* DESC       ：车队确认请求显示脚本文件
* Author     : X
* CREATEDATE ：2004-03-04
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************/
function submitForm()
{
  if(checkHasSelect())
  {
    fm.submit();
  }
}

/*继续录入车队*/
function continueInputSubmitForm()
{
  if(checkContractNo() && checkHasSelect())
  {
    fm.ContinueInputFlag.value = "1";
    fm.submit();
  }
}

/*校验合同号*/
function checkContractNo()
{
  var varContractNo = fm.ContractNo.value;

  if(varContractNo == null || trim(varContractNo) == "")
  {
    errorMessage("请输入继续录入车队的合同号!");
    fm.ContractNo.focus();
    return false;
  }
  return true;
}


function checkHasSelect()
{
  var i = 0;
  var bHasSelect = false;
  var len = getElementCount("CheckedValidNo");
  if(len>1)
  {
    for(i=0;i<len;i++)
    {
      if(fm.CheckedValidNo[i].checked)
      {
        bHasSelect = true;
        break;
      }
    }
  }
  else if(len==1)
  {
    if(fm.CheckedValidNo.checked)
    {
      bHasSelect = true;
    }
  }
  
  if(!bHasSelect)
  {
    errorMessage("没有选择确认码不能录单，请先选择确认码!");
    return false;
  }
  return true;
}
