package com.thetestingacademy.pages.pageFactory.kataloncura;

import com.thetestingacademy.base.CommonToAllPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageKatalon_PF extends CommonToAllPage {

    @FindBy(id = "btn-make-appointment")
    private WebElement makeAppointmentButton;

    public HomePageKatalon_PF(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public HomePageKatalon_PF open(String url) {
        driver().get(url);
        waitUntilVisible(makeAppointmentButton);
        return this;
    }

    public LoginPageKatalon_PF goToLogin() {
        clickElement(makeAppointmentButton);
        return new LoginPageKatalon_PF(driver());
    }
}
