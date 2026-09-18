package com.thetestingacademy.pages.pageFactory.kataloncura;

import com.thetestingacademy.base.CommonToAllPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class AppointmentPageKatalon_PF extends CommonToAllPage {

    public enum HealthcareProgram {
        MEDICARE, MEDICAID, NONE
    }

    @FindBy(css = "#appointment h2")
    private WebElement heading;

    @FindBy(id = "combo_facility")
    private WebElement facility;

    @FindBy(id = "chk_hospotal_readmission")
    private WebElement readmission;

    @FindBy(css = "label[for='chk_hospotal_readmission']")
    private WebElement readmissionLabel;

    @FindBy(id = "radio_program_medicare")
    private WebElement medicare;

    @FindBy(id = "radio_program_medicaid")
    private WebElement medicaid;

    @FindBy(id = "radio_program_none")
    private WebElement none;

    @FindBy(id = "txt_visit_date")
    private WebElement visitDate;

    @FindBy(id = "txt_comment")
    private WebElement comment;

    @FindBy(id = "btn-book-appointment")
    private WebElement bookAppointmentButton;

    public AppointmentPageKatalon_PF(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getHeading() {
        return getText(heading);
    }

    public ConfirmationPageKatalon_PF bookAppointment(
            String facilityName,
            boolean applyForReadmission,
            HealthcareProgram program,
            String date,
            String appointmentComment) {

        new Select(waitUntilVisible(facility)).selectByVisibleText(facilityName);
        setCheckbox(readmission, applyForReadmission);
        selectProgram(program);
        enterDate(date);
        enterComment(appointmentComment);
        verifyFormState(applyForReadmission, date, appointmentComment);

        // WebElement.submit() lost dynamic checkbox/textarea values on some
        // ChromeDriver platforms. requestSubmit() uses the browser's native
        // form validation and serializes the current control values.
        WebElement submitButton = waitUntilClickable(bookAppointmentButton);
        ((JavascriptExecutor) driver()).executeScript(
                "arguments[0].form.requestSubmit(arguments[0]);", submitButton);
        waitFor().until(ExpectedConditions.urlContains("appointment.php#summary"));
        return new ConfirmationPageKatalon_PF(driver());
    }

    private void setCheckbox(WebElement checkbox, boolean expectedState) {
        waitUntilVisible(checkbox);
        if (checkbox.isSelected() != expectedState) {
            // Clicking the visible label is more reliable than clicking the small
            // checkbox input in Linux headless Chrome.
            clickElement(readmissionLabel);
        }

        // The CURA page uses a styled Bootstrap checkbox. Some headless Chrome
        // versions report a successful label click without changing the native
        // input. Keep the real click above, then use a DOM fallback only when the
        // requested state was not applied.
        if (checkbox.isSelected() != expectedState) {
            ((JavascriptExecutor) driver()).executeScript(
                    "arguments[0].checked = arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));" +
                            "arguments[0].dispatchEvent(new Event('change', {bubbles: true}));",
                    checkbox,
                    expectedState);
        }
        waitFor().until(ExpectedConditions.elementSelectionStateToBe(checkbox, expectedState));
    }

    private void verifyFormState(
            boolean expectedReadmission,
            String expectedDate,
            String expectedComment) {
        boolean actualReadmission = readmission.isSelected();
        String actualDate = visitDate.getDomProperty("value");
        String actualComment = comment.getDomProperty("value");

        if (actualReadmission != expectedReadmission) {
            throw new IllegalStateException(
                    "Readmission state mismatch. Expected: " + expectedReadmission
                            + ", actual: " + actualReadmission);
        }
        if (!expectedDate.equals(actualDate)) {
            throw new IllegalStateException(
                    "Visit date mismatch. Expected: '" + expectedDate
                            + "', actual: '" + actualDate + "'");
        }
        if (!expectedComment.equals(actualComment)) {
            throw new IllegalStateException(
                    "Comment mismatch. Expected: '" + expectedComment
                            + "', actual: '" + actualComment + "'");
        }
    }

    private void enterDate(String value) {
        enterInput(visitDate, value);
        visitDate.sendKeys(Keys.TAB);
        setDomValueIfNeeded(visitDate, value);
    }

    private void enterComment(String value) {
        // Prefer a real keyboard interaction. Chrome headless can occasionally
        // drop textarea keystrokes while the legacy date picker loses focus.
        clickElement(comment);
        enterInput(comment, value);
        setDomValueIfNeeded(comment, value);
    }

    private void setDomValueIfNeeded(WebElement element, String value) {
        if (!value.equals(element.getDomProperty("value"))) {
            ((JavascriptExecutor) driver()).executeScript(
                    "arguments[0].value = arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));" +
                            "arguments[0].dispatchEvent(new Event('change', {bubbles: true}));",
                    element,
                    value);
        }
    }

    private void selectProgram(HealthcareProgram program) {
        WebElement programRadio = switch (program) {
            case MEDICARE -> medicare;
            case MEDICAID -> medicaid;
            case NONE -> none;
        };

        clickElement(programRadio);

        // Medicare is selected by default. On some Linux headless Chrome
        // versions the click returns normally but leaves that default intact.
        if (!programRadio.isSelected()) {
            ((JavascriptExecutor) driver()).executeScript(
                    "arguments[0].checked = true;" +
                            "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));" +
                            "arguments[0].dispatchEvent(new Event('change', {bubbles: true}));",
                    programRadio);
        }

        if (!programRadio.isSelected()) {
            throw new IllegalStateException(
                    "Healthcare program was not selected: " + program);
        }
    }
}
