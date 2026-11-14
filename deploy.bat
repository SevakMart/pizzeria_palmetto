@echo off
rem Builds the WAR (optional), copies it to Tomcat's webapps and restarts Tomcat.
rem Usage: deploy.bat [--build]
rem Requires JAVA_HOME (JDK 21) and CATALINA_HOME (Tomcat 11) environment variables.
setlocal

if "%JAVA_HOME%"=="" (
    echo JAVA_HOME is not set. Point it to a JDK 21 installation.
    exit /b 1
)
if "%CATALINA_HOME%"=="" (
    echo CATALINA_HOME is not set. Point it to your Tomcat 11 directory.
    exit /b 1
)

set "PATH=%JAVA_HOME%\bin;%PATH%"
set "PROJECT_DIR=%~dp0"
set "WAR_NAME=pizzeria-palmetto"
set "WAR_SRC=%PROJECT_DIR%target\%WAR_NAME%.war"
set "WEBAPPS=%CATALINA_HOME%\webapps"

if /i "%~1"=="--build" (
    pushd "%PROJECT_DIR%"
    call mvn -q -DskipTests clean package
    if errorlevel 1 (
        popd
        echo Build failed.
        exit /b 1
    )
    popd
)

if not exist "%WAR_SRC%" (
    echo WAR not found: %WAR_SRC% ^(run with --build^)
    exit /b 1
)

echo Stopping Tomcat...
call "%CATALINA_HOME%\bin\shutdown.bat" >nul 2>&1
rem Give Tomcat time to release the exploded webapp files
timeout /t 8 /nobreak >nul

echo Deploying %WAR_NAME%.war...
if exist "%WEBAPPS%\%WAR_NAME%" rmdir /s /q "%WEBAPPS%\%WAR_NAME%"
if exist "%WEBAPPS%\%WAR_NAME%.war" del /q "%WEBAPPS%\%WAR_NAME%.war"
copy /y "%WAR_SRC%" "%WEBAPPS%\%WAR_NAME%.war" >nul
if errorlevel 1 (
    echo Failed to copy the WAR to %WEBAPPS%
    exit /b 1
)

echo Starting Tomcat...
call "%CATALINA_HOME%\bin\startup.bat"

echo Done. Try: http://localhost:8080/%WAR_NAME%/v1/orders
echo Logs: %CATALINA_HOME%\logs\palmetto.log
endlocal
