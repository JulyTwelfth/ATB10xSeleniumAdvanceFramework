package com.thetestingacademy.base;

import com.thetestingacademy.utils.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.thetestingacademy.driver.DriverManager.getDriver;

public class CommonToAllPage {
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);

    public CommonToAllPage() {
        // If you want to call something before every Page Object Class call, Put your Code here");
        // Open File, Open Data Base Connection You can write code here
    }

    public void openVWOUrl(){
        getDriver().get(PropertiesReader.readKey("url"));
    }
    public void openOrangeHRMUrl(){
        getDriver().get(PropertiesReader.readKey("ohr_url"));
    }




    public void clickElement(By by) {
        waitUntilClickable(by).click();
    }
    public void clickElement(WebElement by) {
        waitUntilClickable(by).click();
    }

    public void enterInput(By by, String key) {
        WebElement element = waitUntilVisible(by);
        element.clear();
        element.sendKeys(key);
    }

    public void enterInput(WebElement by, String key) {
        WebElement element = waitUntilVisible(by);
        element.clear();
        element.sendKeys(key);
    }

    public String getText(By by){
        return waitUntilVisible(by).getText().trim();
    }

    public String getText(WebElement by){
        return waitUntilVisible(by).getText().trim();
    }

    protected WebElement waitUntilVisible(By locator) {
        return waitFor().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitUntilVisible(WebElement element) {
        return waitFor().until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitUntilClickable(By locator) {
        return waitFor().until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitUntilClickable(WebElement element) {
        return waitFor().until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebDriverWait waitFor() {
        return new WebDriverWait(getDriver(), DEFAULT_TIMEOUT);
    }

    protected WebDriver driver() {
        return getDriver();
    }

}
