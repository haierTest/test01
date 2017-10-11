/*****************************************************************************
 * DESC       �����������߲�ѯ����¼��ű�����
* Author     : X
 * CREATEDATE ��2003-02-11
 * MODIFYLIST ��   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/


function checkStartDate(field)
{
  if(checkFullDate(field)==false)
    return false;
  fm.EndDate.value = getNextDateFullDate(getNextYearFullDate(field.value,1),-1);
  return true;
}


function checkEndDate(field)
{
  if(checkFullDate(field)==false)
    return false;

  if(compareFullDate(fm.EndDate.value,fm.StartDate.value)<0)
  {
    errorMessage("��XXXXX���ڲ�����ǰ����XXXXX���ڣ�");
    fm.EndDate.focus();
    fm.EndDate.select();
    return false;
  }

  if(compareFullDate(fm.EndDate.value,getNextYearFullDate(fm.StartDate.value,1))>=0)
  {
  	errorMessage("XX�����Ϊ1�꣡");
    fm.EndDate.focus();
    fm.EndDate.select();
    return false;
  }

  return true;
}


function checkForm()
{
  var arrElement = new Array();
  var i = 0;
  

  arrElement[i++] = fm.EngineNo    ;
  arrElement[i++] = fm.CarKindCode ;
  arrElement[i++] = fm.SeatCount   ;
  arrElement[i++] = fm.Amount      ;
  arrElement[i++] = fm.StartDate   ;
  arrElement[i++] = fm.EndDate     ;
  
  
  if(fm.CarKindCode.value=="H0" || fm.CarKindCode.value=="L0")
  {
    arrElement[i++] = fm.TonCount;
  }
  
  if(fm.CarKindCode.value=="M0" || fm.CarKindCode.value=="J0")
  {
    arrElement[i++] = fm.ExhaustScale;
  }
  
  for(i=0; i<arrElement.length; i++)
  {
    if(isEmpty(arrElement[i])==true)
    {
      errorMessage(arrElement[i].description+"����Ϊ��");
      arrElement[i].focus();
      arrElement[i].select();
      return false;
    }
  }
  
  
  return true;
}


function submitForm()
{
  if( !checkForm() ) return;
	fm.submit();
}
