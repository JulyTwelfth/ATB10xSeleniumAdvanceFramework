package com.thetestingacademy.tests.pageFactory.kataloncura;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageFactory.kataloncura.AppointmentPageKatalon_PF;
import com.thetestingacademy.pages.pageFactory.kataloncura.HomePageKatalon_PF;
import com.thetestingacademy.pages.pageFactory.kataloncura.LoginPageKatalon_PF;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.thetestingacademy.driver.DriverManager.getDriver;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("CURA Healthcare Service")
@Feature("Authentication")
@Owner("QA Portfolio")
public class KatalonLoginTest extends CommonToAllTest {

    @Test(description = "QA-KAT-LOGIN-001: Login with valid credentials")
    @Story("Valid login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("A registered user can sign in and reach the appointment form.")
    public void validCredentialsShouldOpenAppointmentForm() {
        LoginPageKatalon_PF loginPage = openLoginPage();

        AppointmentPageKatalon_PF appointmentPage = loginPage.loginSuccessfully(
                PropertiesReader.readKey("katalon_username"),
                PropertiesReader.readKey("katalon_password"));

        assertThat(appointmentPage.getHeading()).isEqualTo("Make Appointment");
        assertThat(getDriver().getCurrentUrl()).contains("#appointment");
    }

    @Test(
            dataProvider = "invalidCredentials",
            description = "QA-KAT-LOGIN-002: Reject invalid or incomplete credentials")
    @Story("Invalid login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Invalid and incomplete credentials display the expected validation message.")
    public void invalidCredentialsShouldShowError(String username, String password, String scenario) {
        LoginPageKatalon_PF loginPage = openLoginPage();

        loginPage.loginExpectingFailure(username, password);

        assertThat(loginPage.getLoginError())
                .as("Login validation for scenario: %s", scenario)
                .isEqualTo(PropertiesReader.readKey("katalon_login_error"));
        assertThat(getDriver().getCurrentUrl()).contains("#login");
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        String validUsername = PropertiesReader.readKey("katalon_username");
        String validPassword = PropertiesReader.readKey("katalon_password");
        String invalidUsername = PropertiesReader.readKey("katalon_invalid_username");
        String invalidPassword = PropertiesReader.readKey("katalon_invalid_password");

        return new Object[][]{
                {validUsername, invalidPassword, "valid username and invalid password"},
                {invalidUsername, validPassword, "invalid username and valid password"},
                {"", validPassword, "empty username"},
                {validUsername, "", "empty password"},
                {"", "", "empty username and password"}
        };
    }

    private LoginPageKatalon_PF openLoginPage() {
        return new HomePageKatalon_PF(getDriver())
                .open(PropertiesReader.readKey("katalon_url"))
                .goToLogin();
    }
}
