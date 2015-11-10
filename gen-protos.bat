@echo off

echo Compiling...

set JAVA_OUT=D:\IdeaProjects\umbrella\umbrella-support\src\main\java\
set PROTO_PATH=D:\IdeaProjects\umbrella\protos\

protoc.exe --java_out=%JAVA_OUT% --proto_path=%PROTO_PATH% %PROTO_PATH%/*.proto

echo Compile completed


