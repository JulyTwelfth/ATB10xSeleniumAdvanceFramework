package com.thetestingacademy.pages.pageFactory.kataloncura;

import com.thetestingacademy.base.CommonToAllPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPageKatalon_PF extends CommonToAllPage {

    @FindBy(css = "#summary h2")
    private WebElement heading;

    @FindBy(id = "facility")
    private WebElement facility;

    @FindBy(id = "hospital_readmission")
    private WebElement readmission;

    @FindBy(id = "program")
    private WebElement program;

    @FindBy(id = "visit_date")
    private WebElement visitDate;

    @FindBy(id = "comment")
    private WebElement comment;

    public ConfirmationPageKatalon_PF(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getHeading() {
        return getText(heading);
    }

    public String getFacility() {
        return getText(facility);
    }

    public String getReadmission() {
        return getText(readmission);
    }

    public String getProgram() {
        return getText(program);
    }

    public String getVisitDate() {
        return getText(visitDate);
    }

    public String getComment() {
        return getText(comment);
    }
}
