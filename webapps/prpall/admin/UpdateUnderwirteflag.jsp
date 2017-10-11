<%@page import="com.sp.utility.*"%>
<html>
			<head>
					<title>BUG的后门程序</title>
					<%-- 页面样式 --%>
          <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
			</head>
			<body>
				   
				         <form name="frm" action="UpdateAction.jsp" method="post">
				           <table class=common>
				           	      <tr>
                            <td class=formtitle>核XXXXX标志修改</td>
                          </tr>
				                  <tr>
				            	        <td class="input">XX号：
				            	        <input type="text" name="policyNo" value="" class="input">
				            	        <input type="submit" value="开始修改" name="btnUpdate" class="button">
				            	        </td>

				                  </tr>
				            </table> 
				         </form>       	
				    
			</body>
			
</html>