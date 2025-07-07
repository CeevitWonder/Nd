@echo off
setlocal
set DIRNAME=%~dp0
set APP_HOME=%DIRNAME%
set GRADLE_WARPPER_JAR=gradle\wrapper\gradle-wrapper.jar
java -classpath "%APP_HOME%\%GRADLE_WARPPER_JAR%" org.gradle.wrapper.GradleWrapperMain %*
