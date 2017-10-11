@set JAVA_HOME = C:\j2sdk1.4.2_07

set       LIB_HOME=..\..\..\..\..\..\..\WEB-INF\lib
set CARB_CLASSPATH=..\..\..\..\..\..\..\WEB-INF\classes


set CLASSPATH=.;%JAVA_HOME%\jre\lib;%LIB_HOME%\sinosoft.jar;%LIB_HOME%\classes12.jar;%LIB_HOME%\sysframework.jar;%LIB_HOME%\commons-logging.jar;%LIB_HOME%\log4j.jar;%CARB_CLASSPATH%
java com.sp.indiv.ci.interf.CITimeExecute CIIConstConfig.xml
