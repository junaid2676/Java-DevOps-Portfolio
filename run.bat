@echo off
echo ========================================
echo   Starting Junaid's Portfolio Server
echo ========================================

setlocal
set "OUT_DIR=out"

if not exist "%OUT_DIR%\com\junaid\portfolio\PortfolioServer.class" (
    echo [INFO] Classes not found. Running build first...
    call build.bat
    if %errorlevel% neq 0 exit /b %errorlevel%
)

java -cp "%OUT_DIR%" com.junaid.portfolio.PortfolioServer %*

endlocal
