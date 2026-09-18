package com.thetestingacademy.driver;

import org.openqa.selenium.WebDriver;

public final class DriverManagerTL {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManagerTL() {
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static void unload() {
        DRIVER.remove();
    }
}
