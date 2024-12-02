@echo off
call npm run build
rmdir /s /q ..\back\src\main\resources\static
mkdir ..\back\src\main\resources\static
xcopy build\* ..\back\src\main\resources\static /E /H /C /I
echo Build and file copy completed successfully.
pause
exit /b
