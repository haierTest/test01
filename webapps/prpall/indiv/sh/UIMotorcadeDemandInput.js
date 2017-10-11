/*
****************************************************************************
* DESC       ：车队查询请求录入脚本文件
* Author     : X
* CREATEDATE ：2004-03-04
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************/

function changeNumCar()
{
  var varNumCar = parseInt(fm.NumCar.value);
  var count = getRowsCount('Motorcade');
  
  var j;
  if(isNaN(varNumCar)) varNumCar = 0;
  
  if(varNumCar>500)
  {
    if(!confirm("你确定车辆数吗?车辆数大于500辆会影响到页面刷新速度."))
    {
      fm.NumCar.value = count;
      return;
    }
  }
  
  if(count<varNumCar) 
  {
    insertMultiCar(varNumCar-count);
  }
  else if(count>varNumCar) 
  {
    if(!confirm("您确定减少车辆数，后面多出的车辆信息将被删除？"))
    {
      fm.NumCar.value = count;
      return;
    } 
    
    var oTBODY   = document.all('Motorcade').tBodies.item(0);
    for(j=count-1;j>=varNumCar;j--)
    {
      oTBODY.deleteRow(j);
      window.status = "正在刷新页面,请等待!";
    }
    window.status = "";
  }
}


function insertSingleCar()
{
  var varCarCount = getRowsCount('Motorcade');

  var varLicenseNo = "";
  var varEngineNo = "";
  var varFrameNo = "";
  var varUseNatureCode= "";
  var varCarKindCode = "";
  var varSeatCount = "";
  var varTonCount = "";
  var varExhaustScale = "";
  var varAmount = "";
  var varStartDate = "";
  var varEndDate = "";
  
  if(varCarCount>=1)
  {
    varLicenseNo     = fm.LicenseNo    [varCarCount].value;
    varEngineNo      = fm.EngineNo     [varCarCount].value;
    varFrameNo       = fm.FrameNo      [varCarCount].value;
    varUseNatureCode = fm.UseNatureCode[varCarCount].value;
    varCarKindCode   = fm.CarKindCode  [varCarCount].value;
    varSeatCount     = fm.SeatCount    [varCarCount].value;
    varTonCount      = fm.TonCount     [varCarCount].value;
    varExhaustScale  = fm.ExhaustScale [varCarCount].value;
    varAmount        = fm.Amount       [varCarCount].value;
    varStartDate     = fm.StartDate    [varCarCount].value;
    varEndDate       = fm.EndDate      [varCarCount].value;      
  }
  else
  {
    varLicenseNo     = fm.LicenseNo    .value;
    varEngineNo      = fm.EngineNo     .value;
    varFrameNo       = fm.FrameNo      .value;
    varUseNatureCode = fm.UseNatureCode.value;
    varCarKindCode   = fm.CarKindCode  .value;
    varSeatCount     = fm.SeatCount    .value;
    varTonCount      = fm.TonCount     .value;
    varExhaustScale  = fm.ExhaustScale .value;
    varAmount        = fm.Amount       .value;
    varStartDate     = fm.StartDate    .value;
    varEndDate       = fm.EndDate      .value;
  }

  insertRow('Motorcade','Motorcade_Data');
  
  fm.SerialNo     [varCarCount+1].value = varCarCount+1;
  fm.LicenseNo    [varCarCount+1].value = varLicenseNo    ;
  fm.EngineNo     [varCarCount+1].value = varEngineNo     ;
  fm.FrameNo      [varCarCount+1].value = varFrameNo      ;
  fm.UseNatureCode[varCarCount+1].value = varUseNatureCode;
  fm.CarKindCode  [varCarCount+1].value = varCarKindCode  ;
  fm.SeatCount    [varCarCount+1].value = varSeatCount    ;
  fm.TonCount     [varCarCount+1].value = varTonCount     ;
  fm.ExhaustScale [varCarCount+1].value = varExhaustScale ;
  fm.Amount       [varCarCount+1].value = varAmount       ;
  fm.StartDate    [varCarCount+1].value = varStartDate    ;
  fm.EndDate      [varCarCount+1].value = varEndDate      ;

  fm.NumCar.value = varCarCount+1;
}


function insertMultiCar(Count)
{
  var carCount = document.all("Motorcade").tBodies.item(0).rows.length;
  if(carCount==0)
  {
    insertRow('Motorcade','Motorcade_Data');
    fm.SerialNo[1].value = "1";
    Count--;
    carCount++;
  }
  
  var i = 0;
  var oldTBodyInnerHTML = document.all("Motorcade").tBodies.item(0).innerHTML;
  var oldTHeadOuterHTML = document.all("Motorcade").tHead.outerHTML;
  var oldTFootOuterHTML = document.all("Motorcade").tFoot.outerHTML;
  var vGenHTML = ""
  var vOneTRHTML = "";
  var vAllHTML = "";
  for(i=carCount+1; i<=carCount+Count; i++)
  {
    fm.SerialNo[carCount].value = i;
    vOneTRHTML = document.all("Motorcade").tBodies.item(0).rows(carCount-1).outerHTML;
    vGenHTML = vGenHTML + vOneTRHTML;
    window.status = "正在增加第"+i+"辆车";
    if(i%500==0 || i==carCount+Count)
    {
      vAllHTML = vAllHTML + vGenHTML;
      vGenHTML = "";
    }
  }
  window.status = "正在刷新页面,请等待! ";
  spanMotorcade.innerHTML = "<table class='common' id='Motorcade'>\n"
                          + oldTHeadOuterHTML
                          + oldTFootOuterHTML
                          + "<tbody>\n"
                          + oldTBodyInnerHTML
                          + vAllHTML
                          + "</tbody>\n"
                          + "<table>\n";

  window.status = "";
}


function deleteSingleCar(Field,intPageDataKeyCount,intRowCount)
{
  deleteRow('Motorcade',Field,intPageDataKeyCount,intRowCount);
}


function checkStartDate(field)
{
  if(checkFullDate(field)==false)
    return false;

  var currentIndex = getElementOrder(field)-1;

  fm.EndDate[currentIndex].value = getNextDateFullDate(getNextYearFullDate(field.value,1),-1);
  return true;
}


function checkEndDate(field)
{
  if(checkFullDate(field)==false)
    return false;
  
  var currentIndex = getElementOrder(field)-1;

  if(compareFullDate(fm.EndDate[currentIndex].value,fm.StartDate[currentIndex].value)<0)
  {
    errorMessage("终XXXXX日期不得提前于起XXXXX日期！");
    fm.EndDate[currentIndex].focus();
    fm.EndDate[currentIndex].select();
    return false;
  }

  if(compareFullDate(fm.EndDate[currentIndex].value,getNextYearFullDate(fm.StartDate[currentIndex].value,1))>=0)
  {
  	errorMessage("XX期限最长为1年！");
    fm.EndDate[currentIndex].focus();
    fm.EndDate[currentIndex].select();
    return false;
  }

  return true;
}


function Contract_Onclick()
{
  if(fm.CheckBoxContract.checked)
  {
    spanOldContractNo.style.display = '';
  }
  else
  {
    spanOldContractNo.style.display = 'none';
  }
}


function renewalMotorcade()
{
  if(fm.OldContractNo.value.length!=22)
  {
    errorMessage("请输入完整合同号!");
    fm.OldContractNo.focus();
    fm.OldContractNo.select();
    return;
  }
  window.location = "/prpall/indiv/sh/UIMotorcadeDemandInput.jsp?OldContractNo="+fm.OldContractNo.value;
}


function checkForm()
{
  var varCarCount = parseInt(fm.NumCar.value);
  var varRowsCount = getRowsCount('Motorcade');

  if(varCarCount != varRowsCount)
  {
    errorMessage("请先确认车队的车辆数！");
    fm.NumCar.focus();
    return false;
  }

  var j = 0;
  for(j=1;j<=varCarCount;j++)
  {
    var arrElement = new Array();
    var i = 0;
    
    arrElement[i++] = fm.FrameNo[j]     ;
    arrElement[i++] = fm.EngineNo[j]    ;
    arrElement[i++] = fm.CarKindCode[j] ;
    arrElement[i++] = fm.SeatCount[j]   ;
    arrElement[i++] = fm.Amount[j]      ;
    arrElement[i++] = fm.StartDate[j]   ;
    arrElement[i++] = fm.EndDate[j]     ;

    
    if(fm.CarKindCode[j].value=="H0" || fm.CarKindCode[j].value=="L0")
    {
      arrElement[i++] = fm.TonCount[j];
    }
    
    if(fm.CarKindCode[j].value=="M0" || fm.CarKindCode[j].value=="J0")
    {
      arrElement[i++] = fm.ExhaustScale[j];
    }
    
    for(i=0; i<arrElement.length; i++)
    {
      if(isEmpty(arrElement[i])==true)
      {
        errorMessage(arrElement[i].description+"不能为空");
        arrElement[i].focus();
        arrElement[i].select();
        return false;
      }
    }
  }
  return true;
}


function submitForm()
{
  if( !checkForm() ) return;
	fm.submit();
}
