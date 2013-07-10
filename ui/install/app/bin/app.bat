@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  app startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

@rem Add default JVM options here. You can also use JAVA_OPTS and APP_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windowz variants

if not "%OS%" == "Windows_NT" goto win9xME_args
if "%@eval[2+2]" == "4" goto 4NT_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*
goto execute

:4NT_args
@rem Get arguments from the 4NT Shell from JP Software
set CMD_LINE_ARGS=%$

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\ui.jar;%APP_HOME%\lib\mongo-java-driver-2.11.1.jar;%APP_HOME%\lib\bson-2.11.1.jar;%APP_HOME%\lib\wicket-core-6.6.0.jar;%APP_HOME%\lib\wicket-spring-6.6.0.jar;%APP_HOME%\lib\wicket-auth-roles-6.6.0.jar;%APP_HOME%\lib\wicket-extensions-6.6.0.jar;%APP_HOME%\lib\wicket-native-websocket-jetty-0.6.jar;%APP_HOME%\lib\wicketstuff-annotation-6.5.0.jar;%APP_HOME%\lib\spring-core-3.2.2.RELEASE.jar;%APP_HOME%\lib\spring-tx-3.2.2.RELEASE.jar;%APP_HOME%\lib\spring-jdbc-3.2.2.RELEASE.jar;%APP_HOME%\lib\spring-beans-3.2.2.RELEASE.jar;%APP_HOME%\lib\spring-context-3.2.2.RELEASE.jar;%APP_HOME%\lib\spring-context-support-3.2.2.RELEASE.jar;%APP_HOME%\lib\spring-web-3.2.2.RELEASE.jar;%APP_HOME%\lib\spring-webmvc-3.2.2.RELEASE.jar;%APP_HOME%\lib\spring-data-mongodb-1.1.1.RELEASE.jar;%APP_HOME%\lib\jetty-all-server-8.1.7.v20120910.jar;%APP_HOME%\lib\javax.servlet-3.0.0.v201112011016.jar;%APP_HOME%\lib\jetty-continuation-8.1.7.v20120910.jar;%APP_HOME%\lib\jetty-http-8.1.7.v20120910.jar;%APP_HOME%\lib\service.jar;%APP_HOME%\lib\slf4j-api-1.7.5.jar;%APP_HOME%\lib\wicket-util-6.6.0.jar;%APP_HOME%\lib\wicket-request-6.6.0.jar;%APP_HOME%\lib\asm-3.3.1.jar;%APP_HOME%\lib\cglib-2.2.2.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\wicket-ioc-6.6.0.jar;%APP_HOME%\lib\wicket-native-websocket-core-0.6.jar;%APP_HOME%\lib\commons-logging-1.1.1.jar;%APP_HOME%\lib\aopalliance-1.0.jar;%APP_HOME%\lib\spring-aop-3.2.2.RELEASE.jar;%APP_HOME%\lib\spring-expression-3.2.2.RELEASE.jar;%APP_HOME%\lib\spring-data-commons-core-1.4.0.RELEASE.jar;%APP_HOME%\lib\javax.servlet-3.0.0.v201112011016.jar;%APP_HOME%\lib\javax.security.auth.message-1.0.0.v201108011116.jar;%APP_HOME%\lib\javax.activation-1.1.0.v201105071233.jar;%APP_HOME%\lib\javax.mail.glassfish-1.4.1.v201005082020.jar;%APP_HOME%\lib\javax.annotation-1.1.0.v201108011116.jar;%APP_HOME%\lib\jetty-util-8.1.7.v20120910.jar;%APP_HOME%\lib\jetty-io-8.1.7.v20120910.jar;%APP_HOME%\lib\repository.jar

@rem Execute app
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %APP_OPTS%  -classpath "%CLASSPATH%" com.comsysto.movie.jetty.JettyStart %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable APP_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%APP_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
