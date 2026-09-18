package com.thetestingacademy.driver;

import com.thetestingacademy.utils.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {

    public static WebDriver getDriver() {
        return DriverManagerTL.getDriver();
    }

    public static void setDriver(WebDriver driver) {
        DriverManagerTL.setDriver(driver);
    }


    // When we want to start the browser
    public static void init() {
            if (getDriver() != null) {
                return;
            }

            // browser - ? chrome, firefox, edge
            String browser = PropertiesReader.readKey("browser");
            browser = browser.toLowerCase();
            boolean headless = Boolean.parseBoolean(PropertiesReader.readKey("headless"));

            switch (browser){
                case "edge" :
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--start-maximized");
                    if (headless) {
                        edgeOptions.addArguments("--headless=new", "--window-size=1920,1080");
                    }
                    setDriver(new EdgeDriver(edgeOptions));
                    break;
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    if (headless) {
                        chromeOptions.addArguments("--headless=new", "--window-size=1920,1080");
                    }
                    setDriver(new ChromeDriver(chromeOptions));
                    break;
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--start-maximized");
                    if (headless) {
                        firefoxOptions.addArguments("-headless", "--width=1920", "--height=1080");
                    }
                    setDriver(new FirefoxDriver(firefoxOptions));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }



    }


    // When we want to close the browser
    public static void down(){
        if (getDriver() != null) {
            getDriver().quit();
            DriverManagerTL.unload();
        }

    }




}
