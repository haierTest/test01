@set JAVA_HOME = C:\j2sdk1.4.2_05

set       LIB_HOME=D:\chends\sunny\prpallNew\prpall\SRC\prpins\modules\webapps\prpall\WEB-INF\lib
set CARB_CLASSPATH=D:\chends\sunny\prpallNew\prpall\SRC\prpins\modules\webapps\prpall\WEB-INF\classes


set CLASSPATH=.;%JAVA_HOME%\jre\lib;%LIB_HOME%\sinosoft.jar;%LIB_HOME%\classes12.jar;%LIB_HOME%\sysframework.jar;%LIB_HOME%\commons-logging.jar;%LIB_HOME%\log4j.jar;%CARB_CLASSPATH%
java com.sp.indiv.ci.interf.CIInsureCompare CIIConstConfig.xml