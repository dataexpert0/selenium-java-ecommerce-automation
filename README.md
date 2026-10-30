# Selenium Java E-commerce Automation

Automated functional testing project for the [SauceDemo](https://www.saucedemo.com/) e-commerce application, developed using **Java, Selenium WebDriver, JUnit 5, and Maven**.

The project aims to demonstrate *software testing practices*, *test automation*, and *maintainable test architecture* using the **Page Object Model (POM)** design pattern.

## Tech Stack

- **Java** — Programming language
- **Selenium WebDriver** — Browser automation
- **JUnit 5** — Test execution, assertions, and parameterized tests
- **Apache Maven** — Dependency management and build automation
- **Git & GitHub** — Version control

## Project Structure

```text
src/
└── test/
    └── java/
        └── com/example/qa/
            ├── pages/
                └── LoginPage.java
                └── InventoryPage.java
                └── CartPage.java
            └── tests/
                └── LoginTest.java
                └── InventoryTest.java
                └── CartTest.java
```

The project follows the Page Object Model pattern to separate page interactions from test logic.

- `pages/`: Contains page objects, element locators, and interaction methods.
- `tests/`: Contains automated test scenarios and assertions.

## Implemented Test Scenarios

### Authentication

| Test scenario | Expected result |
|---|---|
| Login with valid credentials | User successfully accesses the inventory page |
| Login with a locked-out user | Access is denied |
| Login with an incorrect password | Authentication error message is displayed |
| Login with a nonexistent user | Authentication error message is displayed |
| Login with an empty password | Required password error is displayed |

The authentication suite includes **parameterized testing** using JUnit 5 `@ParameterizedTest` and `@CsvSource`, enabling multiple input combinations to be tested with reusable logic.

### Inventory and Product Tests
---

| Test ID | Test Scenario | Expected Result |
|---|---|---|
| INV-001 | Verify product catalog display | The inventory page displays at least one product |
| INV-002 | Sort products by price (low to high) | Products are displayed in ascending price order |
| INV-003 | Add a product to the shopping cart | The shopping cart badge displays `1` |

**Test Class:** `InventoryTest.java`

**Page Object:** `InventoryPage.java`

These tests validate product listing, price sorting, and shopping cart interactions using Selenium WebDriver and JUnit 5.

The price sorting test uses Java collections and comparators to independently verify the order of product prices.

---

### Shopping Cart Tests

| Test ID | Test Scenario | Expected Result |
|---|---|---|
| CART-001 | Add a product to the cart | The selected product appears in the cart |
| CART-002 | Add two products to the cart | Both products are present |
| CART-003 | Remove a product from the cart | The removed product is no longer displayed |
| CART-004 | Verify product price consistency | The product price matches the inventory price |

**Test Class:** `CartTest.java`  
**Page Object:** `CartPage.java`

These scenarios validate cart operations, product identification, removal behavior, and price consistency across different pages.

---

### Checkout Information Tests

| Test ID | Test Scenario | Expected Result |
|---|---|---|
| CHK-001 | Proceed to checkout with valid customer information | The user is redirected to the checkout overview page |
| CHK-002 | Attempt checkout with an empty first name | An error message is displayed indicating that the first name is required |
| CHK-003 | Attempt checkout with an empty last name | An error message is displayed indicating that the last name is required |
| CHK-004 | Attempt checkout with an empty postal code | An error message is displayed indicating that the postal code is required |

**Test Class:** `CheckoutTest.java`

**Page Object:** `CheckoutInformationPage.java`

**Test Coverage:** 4 test scenarios implemented through 2 test methods (1 standard test and 1 parameterized test with 3 data sets).

These tests validate the checkout information form, including successful navigation with valid customer information and validation of required fields.

The negative scenarios use JUnit 5 `@ParameterizedTest` and `@CsvSource` to verify multiple input combinations without duplicating test logic.

Each invalid submission is verified against two conditions:
- The application displays the expected error message.
- The user remains on the checkout information page.

## Running the Tests

### Prerequisites

- JDK 21 or newer
- Apache Maven
- Google Chrome

### Execution

Clone the repository:

```bash
git clone https://github.com/dataexpert0/selenium-java-ecommerce-automation.git
```

Navigate to the project directory:

```bash
cd selenium-java-ecommerce-automation
```

Run all automated tests:

```bash
mvn test
```

Run the login test class only:

```bash
mvn -Dtest=LoginTest test
```

Maven will compile the project, resolve dependencies, and execute the automated tests.

## Testing Practices

This project currently demonstrates:

- **Functional UI testing**
- **Positive** and **negative** test scenarios
- Data-driven testing with **JUnit 5**
- **Page Object Model (POM)** architecture
- **Assertions** and **expected-result** validation
- Test isolation using `@BeforeEach` and `@AfterEach`
- **Explicit waits for synchronization**

## Roadmap

- [x] Configure Java, Maven, Selenium, and JUnit 5
- [x] Implement Page Object Model for authentication
- [x] Implement positive and negative login scenarios
- [x] Implement parameterized login tests
- [x] Automate inventory and product validation
- [x] Automate shopping cart operations
- [x] Automate checkout functionality
- [x] Add automated test reports
- [x] Configure GitHub Actions for continuous integration (CI)

## Application Under Test

[**SauceDemo — Swag Labs**](https://www.saucedemo.com/)

SauceDemo is a demonstration e-commerce application provided by Sauce Labs for testing and automation practice.

## Author

Developed by [dataexpert0](https://github.com/dataexpert0) as a practical software quality assurance and test automation project.
