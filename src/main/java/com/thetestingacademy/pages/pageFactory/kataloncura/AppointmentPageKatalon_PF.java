package com.thetestingacademy.pages.pageFactory.kataloncura;

import com.thetestingacademy.base.CommonToAllPage;
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
        enterInput(visitDate, date);
        visitDate.sendKeys(Keys.TAB);
        // The Bootstrap date picker closes on an actual click outside the widget.
        clickElement(comment);
        enterInput(comment, appointmentComment);
        waitUntilClickable(bookAppointmentButton).submit();
        waitFor().until(ExpectedConditions.urlContains("appointment.php#summary"));
        return new ConfirmationPageKatalon_PF(driver());
    }

    private void setCheckbox(WebElement checkbox, boolean expectedState) {
        if (checkbox.isSelected() != expectedState) {
            clickElement(checkbox);
        }
    }

    private void selectProgram(HealthcareProgram program) {
        switch (program) {
            case MEDICARE -> clickElement(medicare);
            case MEDICAID -> clickElement(medicaid);
            case NONE -> clickElement(none);
        }
    }
}
