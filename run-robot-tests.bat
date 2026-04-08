@echo off
REM ============================================
REM Robot Framework Test Execution Script
REM ============================================

echo =============================================
echo Todo App - Robot Framework UI Tests
echo =============================================
echo.

REM Check if Robot Framework is installed
echo [Step 1] Checking Robot Framework installation...
python --version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Python is not installed!
    echo Please install Python from https://www.python.org/
    pause
    exit /b 1
)

python -c "import robot" >nul 2>&1
if errorlevel 1 (
    echo Robot Framework is not installed. Installing...
    pip install robotframework
    pip install robotframework-seleniumlibrary
    echo.
)

REM Check if application is running
echo [Step 2] Checking if application is running on http://localhost:8080...
powershell -Command "try { $response = Invoke-WebRequest -Uri 'http://localhost:8080' -UseBasicParsing -TimeoutSec 5; Write-Host 'Application is running!' -ForegroundColor Green } catch { Write-Host 'ERROR: Application is not running!' -ForegroundColor Red; exit 1 }"
if errorlevel 1 (
    echo.
    echo Application is NOT running. Please start it first:
    echo   mvn spring-boot:run
    echo.
    pause
    exit /b 1
)
echo.

REM Run Robot Framework tests
echo [Step 3] Running Robot Framework Tests...
echo This will open Chrome browser and perform automated testing.
echo.

robot --outputdir robot-results todo_robot_tests.robot

if errorlevel 1 (
    echo.
    echo =============================================
    echo Tests FAILED - Check robot-results/report.html
    echo =============================================
) else (
    echo.
    echo =============================================
    echo All Robot Tests PASSED Successfully!
    echo Test Report: robot-results/report.html
    echo Test Log: robot-results/log.html
    echo =============================================
)

echo.
pause
