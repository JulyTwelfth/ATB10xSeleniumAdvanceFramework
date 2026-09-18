# CURA Healthcare Service Test Plan

## Objective

Validate the highest-risk user journeys in the public CURA Healthcare Service demo: authentication and appointment booking. This project demonstrates UI test design, PageFactory, data-driven testing, assertions, reporting, and failure evidence for a QA portfolio.

## Scope

In scope:

- Successful login with the documented demo account
- Invalid and incomplete login combinations
- Appointment creation with facility, readmission, program, date, and comment
- Confirmation-page validation of every submitted value
- Chrome, Edge, and Firefox execution through configuration

Out of scope:

- Real patient or payment data
- API, performance, penetration, accessibility, and mobile testing
- The availability and internal correctness of the third-party demo environment

## Approach

- PageFactory separates locators/actions from test assertions.
- TestNG DataProvider covers invalid credential partitions.
- Explicit waits replace fixed sleeps.
- A fresh browser is created for every test invocation.
- Failed tests save screenshots in `failure_screenshots/`.
- TestNG and Allure result files are generated under `target/`.

## Entry criteria

- Java 21 and Maven 3.9+ are available.
- A supported browser is installed.
- The CURA demo URL is reachable.

## Exit criteria

- All critical scenarios execute.
- No blocker or critical test fails because of the application.
- Any environment-related failure is documented separately from product defects.

## Risks

- The public demo can be unavailable or changed without notice.
- Browser/driver download may require network access on the first run.
- UI locators can change when the demo is updated.
