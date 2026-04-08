#!/bin/bash

echo "========================================"
echo "Running All Unit Tests for Todo App"
echo "========================================"
echo ""

# Run all tests using Maven
mvn clean test

echo ""
echo "========================================"
echo "Test Execution Completed"
echo "========================================"
echo ""
echo "To see detailed test report, check:"
echo "target/surefire-reports/"
echo ""
