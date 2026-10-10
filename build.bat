@echo off
echo ========================================
echo   Building Junaid's Portfolio
echo ========================================

setlocal
set "SRC_DIR=src\main\java"
set "OUT_DIR=out"

if exist "%OUT_DIR%" rmdir /s /q "%OUT_DIR%"
mkdir "%OUT_DIR%"

echo Compiling Java sources...
javac -d "%OUT_DIR%" -encoding UTF-8 "%SRC_DIR%\com\junaid\portfolio\model\ContactMessage.java" "%SRC_DIR%\com\junaid\portfolio\handler\PortfolioHandler.java" "%SRC_DIR%\com\junaid\portfolio\PortfolioServer.java"

if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed.
    exit /b %errorlevel%
)

echo Copying web resources...
xcopy /e /i /y "src\main\resources\web" "%OUT_DIR%\web" >nul

echo.
echo [SUCCESS] Build complete. Run run.bat to start the server.
endlocal
