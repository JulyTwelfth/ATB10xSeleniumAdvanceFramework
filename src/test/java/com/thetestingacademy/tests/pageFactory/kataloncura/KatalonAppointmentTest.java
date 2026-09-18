package com.thetestingacademy.tests.pageFactory.kataloncura;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageFactory.kataloncura.AppointmentPageKatalon_PF;
import com.thetestingacademy.pages.pageFactory.kataloncura.ConfirmationPageKatalon_PF;
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
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.thetestingacademy.driver.DriverManager.getDriver;
import static com.thetestingacademy.pages.pageFactory.kataloncura.AppointmentPageKatalon_PF.HealthcareProgram.MEDICAID;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("CURA Healthcare Service")
@Feature("Appointment booking")
@Owner("QA Portfolio")
public class KatalonAppointmentTest extends CommonToAllTest {

    private static final DateTimeFormatter CURA_DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Test(description = "QA-KAT-APPT-001: Book and verify a healthcare appointment")
    @Story("Create an appointment")
    @Severity(SeverityLevel.BLOCKER)
    @Description("A logged-in user can submit an appointment and see every selected value on the confirmation page.")
    public void userShouldBookAndVerifyAppointment() {
        String facility = PropertiesReader.readKey("katalon_facility");
        String comment = PropertiesReader.readKey("katalon_comment");
        String visitDate = LocalDate.now().plusDays(7).format(CURA_DATE_FORMAT);

        LoginPageKatalon_PF loginPage = new HomePageKatalon_PF(getDriver())
                .open(PropertiesReader.readKey("katalon_url"))
                .goToLogin();

        AppointmentPageKatalon_PF appointmentPage = loginPage.loginSuccessfully(
                PropertiesReader.readKey("katalon_username"),
                PropertiesReader.readKey("katalon_password"));

        ConfirmationPageKatalon_PF confirmationPage = appointmentPage.bookAppointment(
                facility,
                true,
                MEDICAID,
                visitDate,
                comment);

        assertThat(confirmationPage.getHeading()).isEqualTo("Appointment Confirmation");
        assertThat(confirmationPage.getFacility()).isEqualTo(facility);
        assertThat(confirmationPage.getReadmission()).isEqualTo("Yes");
        assertThat(confirmationPage.getProgram()).isEqualTo("Medicaid");
        assertThat(confirmationPage.getVisitDate()).isEqualTo(visitDate);
        assertThat(confirmationPage.getComment()).isEqualTo(comment);
        assertThat(getDriver().getCurrentUrl()).contains("appointment.php#summary");
    }
}
