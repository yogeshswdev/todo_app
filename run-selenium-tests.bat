@echo off
REM ============================================
REM Selenium UI Test Execution Script (Windows)
REM ============================================

echo =============================================
echo Todo App - Selenium UI Test Suite
echo =============================================
echo.

REM Check if application is running
echo [Step 1] Checking if application is running on http://localhost:8080...
powershell -Command "try { $response = Invoke-WebRequest -Uri 'http://localhost:8080' -UseBasicParsing -TimeoutSec 5; Write-Host 'Application is running!' -ForegroundColor Green } catch { Write-Host 'ERROR: Application is not running!' -ForegroundColor Red; Write-Host 'Please start the application first with: mvn spring-boot:run' -ForegroundColor Yellow; exit 1 }"
if errorlevel 1 (
    echo.
    echo Application is NOT running. Please start it first:
    echo   mvn spring-boot:run
    echo.
    pause
    exit /b 1
)
echo.

REM Run Selenium tests
echo [Step 2] Running Selenium UI Tests...
echo This will open Chrome browser and perform automated testing.
echo.

mvn test -Dtest=SeleniumUITestSuite

if errorlevel 1 (
    echo.
    echo =============================================
    echo Tests FAILED - Check output above for errors
    echo =============================================
    pause
    exit /b 1
) else (
    echo.
    echo =============================================
    echo All Selenium Tests PASSED Successfully!
    echo =============================================
)

echo.
pause
