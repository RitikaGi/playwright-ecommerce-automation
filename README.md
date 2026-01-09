# 🎭 Playwright E-Commerce Test Automation

![Tests](https://img.shields.io/badge/tests-60-blue)
![Pass Rate](https://img.shields.io/badge/pass%20rate-95%25-success)
![Java](https://img.shields.io/badge/Java-11-orange)
![Playwright](https://img.shields.io/badge/Playwright-1.38.0-45ba4b)

Comprehensive end-to-end test automation framework for SauceDemo e-commerce platform using Playwright, TestNG, and Allure Reports.

## 📑 Table of Contents
- [Features](#features)
- [Test Coverage](#test-coverage)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running Tests](#running-tests)
- [Reports](#reports)
- [CI/CD Pipeline](#cicd-pipeline)
- [Known Issues](#known-issues)
- [Configuration](#configuration)

## ✨ Features

- **60 Comprehensive Test Cases** covering critical e-commerce workflows
- **Page Object Model (POM)** design pattern for maintainability
- **Allure Reports** with detailed test execution insights and trend analysis
- **CI/CD Integration** with GitHub Actions for automated test execution
- **Screenshot & Trace Capture** on test failures for debugging
- **Parallel Execution** support (configurable in testng.xml)
- **Headless Mode** auto-detection for CI/CD environments
- **Cross-browser Support** (Chromium, Firefox, WebKit via configuration)
- **Data-driven Testing** with TestNG DataProviders
- **Configurable Test Execution** via properties file

## 🧪 Test Coverage

| Module | Test Cases | Status |
|--------|-----------|--------|
| 🔐 Login Tests | 8 | ✅ All Passing |
| 📦 Product Catalog | 10 | ✅ All Passing |
| 🛒 Cart Operations | 10 | ✅ 9/10 Passing* |
| 💳 Checkout Flow | 16 | ✅ All Passing |
| 🧭 Navigation & UI | 8 | ✅ 6/8 Passing* |

**Total: 57/60 tests active** (3 disabled due to known application bugs)

*See [Known Issues](#known-issues) for details on disabled tests.

### Key Test Scenarios
- ✅ User authentication (valid/invalid credentials, locked users)
- ✅ Product browsing, filtering, and sorting
- ✅ Shopping cart operations (add/remove/update)
- ✅ Complete checkout flow with validation
- ✅ Navigation and UI element verification
- ✅ Form validation and error handling
- ✅ Data-driven postal code format testing

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Java** | 11 | Programming Language |
| **Playwright** | 1.38.0 | Browser Automation |
| **TestNG** | 7.11.0 | Test Framework |
| **Maven** | 3.x | Build & Dependency Management |
| **Allure** | 2.32.0 | Test Reporting |
| **GitHub Actions** | - | CI/CD Pipeline |

### Design Patterns & Practices
- **Page Object Model (POM)** - Separate page logic from tests
- **Base Test Class** - Common setup/teardown for all tests
- **Utility Classes** - ConfigReader, TestData management
- **Data-Driven Testing** - TestNG DataProviders for parameterization

## 📁 Project Structure
```
playwright-ecommerce-automation/
│
├── src/
│   ├── main/java/
│   │   ├── pages/              # Page Object Models
│   │   │   ├── LoginPage.java
│   │   │   ├── InventoryPage.java
│   │   │   ├── CartPage.java
│   │   │   ├── CheckoutPage.java
│   │   │   ├── ProductPage.java
│   │   │   ├── HeaderPage.java
│   │   │   ├── FooterPage.java
│   │   │   └── NavigationPage.java
│   │   │
│   │   └── utils/              # Helper classes
│   │       ├── ConfigReader.java
│   │       └── TestData.java
│   │
│   ├── test/java/
│   │   └── tests/              # Test classes
│   │       ├── BaseTest.java
│   │       ├── LoginTest.java
│   │       ├── ProductCatalogTest.java
│   │       ├── CartOperationsTest.java
│   │       ├── CheckoutFlowTest.java
│   │       └── NavigationAndUITest.java
│   │
│   └── test/resources/
│       └── config.properties   # Configuration file
│
├── .github/
│   └── workflows/
│       └── playwright-tests.yml  # CI/CD pipeline
│
├── testng.xml                  # TestNG suite configuration
├── pom.xml                     # Maven dependencies
└── README.md                   # Project documentation
```

## ⚙️ Prerequisites

Before running the tests, ensure you have the following installed:

- **Java Development Kit (JDK) 11** or higher
```bash
  java -version
```

- **Apache Maven 3.6+**
```bash
  mvn -version
```

- **Git** (for cloning the repository)
```bash
  git --version
```

- **Chromium Browser** (will be installed via Playwright CLI)

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/YourUsername/playwright-ecommerce-automation.git
cd playwright-ecommerce-automation
```

### 2. Install Maven Dependencies
```bash
mvn clean install -DskipTests
```

### 3. Install Playwright Browsers
```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chromium"
```

### 4. Configure Test Environment (Optional)
Edit `src/test/resources/config.properties`:
```properties
baseUrl=https://www.saucedemo.com
browser=chrome
headless=auto
```

**Note:** Replace `YourUsername` in clone URL with your actual GitHub username

## 🏃‍♂️ Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=LoginTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=CheckoutFlowTest#TC_CHECKOUT_001_Checkout_Step_One_Valid_Info
```

### Run Tests in Headless Mode
```bash
mvn test -Dheadless=true
```

### Run Tests with Custom Browser
```bash
mvn test -Dbrowser=firefox
```

### Generate Allure Report After Tests
```bash
mvn allure:serve
```
This will automatically open the report in your default browser.

## 📊 Reports

### Allure Reports

This project uses Allure for comprehensive test reporting with visual insights.

#### View Reports Locally
After test execution, generate and view the report:
```bash
mvn allure:serve
```

#### Report Features
- ✅ **Test execution overview** with pass/fail statistics
- 📈 **Trend charts** showing test history across builds
- 🖼️ **Screenshots** attached for failed tests
- 🔍 **Playwright traces** for detailed debugging
- ⏱️ **Execution time** tracking for each test
- 📝 **Detailed error messages** and stack traces

#### Live Report
🔗 **[View Latest Test Report](https://ritikagi.github.io/playwright-ecommerce-automation/)**

*Note: Replace the URL above with your actual GitHub Pages URL*

#### Sample Report
![Allure Report Overview](path-to-screenshot)

*Tip: You can add your Allure report screenshot to the repo and link it here*

## 🔄 CI/CD Pipeline

### GitHub Actions Workflow

Tests run automatically on:
- ✅ **Push** to `main` or `master` branch
- ✅ **Pull Requests** to `main` or `master` branch
- ✅ **Scheduled runs** - Monday to Friday at 3:30 AM UTC
- ✅ **Manual trigger** via "Run workflow" button

### Pipeline Steps
1. **Checkout code** from repository
2. **Setup Java 11** environment
3. **Install Maven dependencies**
4. **Install Playwright browsers**
5. **Execute test suite**
6. **Generate Allure report** with history
7. **Deploy report** to GitHub Pages
8. **Upload artifacts** (reports, screenshots, traces)

### Workflow Configuration
Location: `.github/workflows/playwright-tests.yml`

### Viewing CI/CD Results
- Go to **Actions** tab in your GitHub repository
- Click on latest work

## 🐛 Known Issues

The following tests are currently disabled due to known application bugs:

### BUG-001: Problem User - Image Display Issue
**Test:** `TC_LOGIN_007_Problem_User_Login`  
**Description:** All product images show the same image (dog picture) for problem_user  
**Status:** Known application bug  
**Impact:** Visual verification fails for problem user account

### BUG-002: Reset App State - Cart Page Issue
**Test:** `TC_NAV_008_Reset_App_State_From_Cart`  
**Description:** Reset App State doesn't clear cart when triggered from cart page  
**Status:** Known application bug  
**Impact:** Cart items persist after reset when on cart page

### BUG-003: Empty Cart Checkout
**Test:** `TC_CART_010_Proceed_to_Checkout_from_Cart_Without_Item`  
**Description:** Application allows proceeding to checkout with empty cart  
**Status:** Known application bug  
**Impact:** Should block checkout when cart is empty

---

**Note:** These tests are marked with `enabled=false` in the test classes to prevent false failures in CI/CD pipeline. They can be re-enabled once the application bugs are fixed.

## ⚙️ Configuration

### config.properties

Located at: `src/test/resources/config.properties`
```properties
# Application URL
baseUrl=https://www.saucedemo.com

# Browser configuration
# Options: chrome, firefox, webkit, msedge
browser=chrome

# Headless mode
# Options: true, false, auto (auto-detects CI environment)
headless=auto
```

### Configuration Options

| Property | Values | Default | Description |
|----------|--------|---------|-------------|
| `baseUrl` | Any URL | saucedemo.com | Application base URL |
| `browser` | chrome, firefox, webkit, msedge | chrome | Browser to use |
| `headless` | true, false, auto | auto | Headless mode setting |

### Headless Mode Behavior
- `true` - Always runs in headless mode
- `false` - Always shows browser window
- `auto` - Headless in CI/CD, headed locally

### Test Users (from TestData.java)
- `standard_user` - Normal user access
- `locked_out_user` - Account locked
- `problem_user` - Visual bugs
- `performance_glitch_user` - Slow response

All users password: `secret_sauce`