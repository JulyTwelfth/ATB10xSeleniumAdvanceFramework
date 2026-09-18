# CURA Test Cases

| ID | Priority | Scenario | Test data | Expected result | Automated |
|---|---|---|---|---|---|
| QA-KAT-LOGIN-001 | P0 | Login with valid credentials | Demo username and password | Appointment form is displayed | Yes |
| QA-KAT-LOGIN-002-A | P1 | Valid username, invalid password | Valid user / invalid password | Login is rejected with validation message | Yes |
| QA-KAT-LOGIN-002-B | P1 | Invalid username, valid password | Invalid user / valid password | Login is rejected with validation message | Yes |
| QA-KAT-LOGIN-002-C | P1 | Empty username | Empty user / valid password | Login is rejected with validation message | Yes |
| QA-KAT-LOGIN-002-D | P1 | Empty password | Valid user / empty password | Login is rejected with validation message | Yes |
| QA-KAT-LOGIN-002-E | P1 | Both credentials empty | Empty user / empty password | Login is rejected with validation message | Yes |
| QA-KAT-APPT-001 | P0 | Create and verify appointment | Hongkong facility, readmission Yes, Medicaid, future date, comment | Confirmation displays all submitted values | Yes |

## Exploratory follow-ups

- Validate all facility and healthcare-program combinations.
- Test past, current, invalid, and boundary dates.
- Verify navigation, history, logout, session timeout, and browser back behavior.
- Check keyboard-only operation and visible labels for accessibility.
