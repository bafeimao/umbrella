@echo off

set PROTOC_PATH=D:/github/protoc-v3.0.0-alpha-4.1/cmake/build/solution/Release/
set IMPORT_PATH=D:/IdeaProjects/umbrella/protos
set DST_DIR=D:/IdeaProjects/umbrella/umbrella-support/src/main/java

echo Compiling proto(s) ...

%PROTOC_PATH%/protoc.exe --java_out=%DST_DIR% ./msg.common.proto

echo Compile Completed!!!

pause>nul

