#!/bin/bash
# ============================================
# Selenium UI Test Execution Script (Linux/Mac)
# ============================================

echo "============================================="
echo "Todo App - Selenium UI Test Suite"
echo "============================================="
echo ""

# Check if application is running
echo "[Step 1] Checking if application is running on http://localhost:8080..."
if curl -s http://localhost:8080 > /dev/null; then
    echo "✓ Application is running!"
else
    echo "✗ ERROR: Application is not running!"
    echo "Please start the application first with: mvn spring-boot:run"
    exit 1
fi
echo ""

# Run Selenium tests
echo "[Step 2] Running Selenium UI Tests..."
echo "This will open Chrome browser and perform automated testing."
echo ""

mvn test -Dtest=SeleniumUITestSuite

if [ $? -eq 0 ]; then
    echo ""
    echo "============================================="
    echo "All Selenium Tests PASSED Successfully!"
    echo "============================================="
else
    echo ""
    echo "============================================="
    echo "Tests FAILED - Check output above for errors"
    echo "============================================="
    exit 1
fi
