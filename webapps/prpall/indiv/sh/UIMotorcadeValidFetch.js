/*
****************************************************************************
* DESC       ������ȷ��������ʾ�ű��ļ�
* Author     : X
* CREATEDATE ��2004-03-04
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************/
function submitForm()
{
  if(checkHasSelect())
  {
    fm.submit();
  }
}

/*����¼�복��*/
function continueInputSubmitForm()
{
  if(checkContractNo() && checkHasSelect())
  {
    fm.ContinueInputFlag.value = "1";
    fm.submit();
  }
}

/*У���ͬ��*/
function checkContractNo()
{
  var varContractNo = fm.ContractNo.value;

  if(varContractNo == null || trim(varContractNo) == "")
  {
    errorMessage("���������¼�복�ӵĺ�ͬ��!");
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
    errorMessage("û��ѡ��ȷ���벻��¼��������ѡ��ȷ����!");
    return false;
  }
  return true;
}
