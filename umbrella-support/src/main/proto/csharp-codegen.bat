@echo off

set PROTOC_CMD=C:\portable\protoc-3.0.0-beta-1-win32\protoc.exe
set DST_DIR="C:\Users\Administrator\Documents\visual studio 2013\Projects\ConsoleApplication1\ConsoleApplication1\Generated"
set SRC_DIR=C:\IdeaProjects\umbrella\umbrella-support\src\main\proto
set IMPORT_PATH=C:\IdeaProjects\umbrella\umbrella-support\src\main\proto

rem echo PROTOC_CMD=%PROTOC_CMD%
rem echo DST_DIR=%DST_DIR%
rem echo SRC_DIR=%SRC_DIR%

echo Compiling %SRC_DIR%\*.protos ...

%PROTOC_CMD% -I=%IMPORT_PATH% --csharp_out=%DST_DIR% %SRC_DIR%\*.proto

echo Completed!

pause>nul