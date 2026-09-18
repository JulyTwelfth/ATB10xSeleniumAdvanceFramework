package com.thetestingacademy.pages.pageFactory.kataloncura;

import com.thetestingacademy.base.CommonToAllPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageKatalon_PF extends CommonToAllPage {

    @FindBy(id = "txt-username")
    private WebElement username;

    @FindBy(id = "txt-password")
    private WebElement password;

    @FindBy(id = "btn-login")
    private WebElement loginButton;

    @FindBy(css = "p.lead.text-danger")
    private WebElement loginError;

    public LoginPageKatalon_PF(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public AppointmentPageKatalon_PF loginSuccessfully(String user, String pass) {
        enterCredentials(user, pass);
        clickElement(loginButton);
        return new AppointmentPageKatalon_PF(driver());
    }

    public void loginExpectingFailure(String user, String pass) {
        enterCredentials(user, pass);
        clickElement(loginButton);
    }

    public String getLoginError() {
        return getText(loginError);
    }

    private void enterCredentials(String user, String pass) {
        enterInput(username, user);
        enterInput(password, pass);
    }
}
