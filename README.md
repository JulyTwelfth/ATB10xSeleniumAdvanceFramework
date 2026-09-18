# CURA Healthcare QA Automation Portfolio

UI automation project for the public [CURA Healthcare Service](https://katalon-demo-cura.herokuapp.com/) demo. The Katalon suite is the default Maven test suite and demonstrates practical QA skills: test design, PageFactory, data-driven negative coverage, cross-browser configuration, explicit waits, failure screenshots, and test reporting.

## Automated coverage

- Valid login and appointment-page verification
- Five invalid or incomplete credential combinations
- End-to-end appointment booking
- Confirmation of facility, readmission, program, visit date, and comment
- Seven TestNG test invocations in total

The supporting QA artifacts are in:

- [`docs/TEST_PLAN.md`](docs/TEST_PLAN.md)
- [`docs/TEST_CASES.md`](docs/TEST_CASES.md)

## Tech stack

- Java 21
- Selenium WebDriver 4
- TestNG
- Maven
- AssertJ
- Page Object Model with PageFactory
- Allure result integration
- Log4j2

## Project structure

```text
src/main/java/com/thetestingacademy/
├── base/                 Shared page actions and explicit waits
├── driver/               Browser creation and ThreadLocal lifecycle
├── pages/pageFactory/
│   └── kataloncura/      Home, login, appointment, confirmation pages
└── utils/                Configuration and wait utilities

src/test/java/com/thetestingacademy/
├── base/                 TestNG browser setup/teardown
├── listeners/            Failure screenshot listener
└── tests/pageFactory/
    └── kataloncura/      Login and appointment tests
```

## Prerequisites

- JDK 21+
- Maven 3.9+
- Chrome, Edge, or Firefox
- Network access to the public demo and the browser-driver download host on first execution

Verify the tools:

```powershell
java -version
mvn -version
```

If a terminal opened before Maven was added to `PATH`, close and reopen that terminal.

## Run the tests

The default configuration uses Chrome with a visible browser:

```powershell
mvn clean test
```

Run headless Chrome:

```powershell
mvn clean test -Dbrowser=chrome -Dheadless=true
```

Run Firefox:

```powershell
mvn clean test -Dbrowser=firefox
```

Configuration values can be overridden with Maven system properties. The defaults are stored in [`src/main/resources/data.properties`](src/main/resources/data.properties).

## Reports and evidence

After a run:

- Surefire/TestNG results: `target/surefire-reports/`
- Allure raw results: `target/allure-results/`
- Failure screenshots: `failure_screenshots/`

If the Allure CLI is installed:

```powershell
allure serve target/allure-results
```

## Test design notes

- Page classes contain locators and user actions; test classes own assertions.
- Explicit waits are used instead of fixed `Thread.sleep` calls in the CURA flow.
- Invalid credential partitions are covered with a TestNG `DataProvider`.
- Each test invocation receives a fresh browser session.
- The appointment date is generated dynamically as seven days in the future.
- The driver is stored in `ThreadLocal`, so the lifecycle is ready for controlled parallel execution.

This project targets an intentionally public demo application. Do not reuse the suite against third-party production systems without written authorization.
