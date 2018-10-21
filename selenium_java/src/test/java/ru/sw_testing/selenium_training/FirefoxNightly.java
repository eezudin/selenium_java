package ru.sw_testing.selenium_training;

import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FirefoxNightly {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void start() {

        FirefoxOptions options = new FirefoxOptions()
                .setBinary("C:\\Program Files\\Firefox Nightly\\firefox.exe");
        driver = new FirefoxDriver(options);
        System.out.println(((HasCapabilities)driver).getCapabilities());
        wait = new WebDriverWait(driver, 20);
        }

        @Test
        public void myFirstTest() {
            driver.get("http://localhost/litecart/admin");
            driver.findElement(By.name("username")).sendKeys("admin");
            driver.findElement(By.name("password")).sendKeys("admin");
            driver.findElement(By.name("login")).click();
        }

        @AfterTest
        public void stop() {
            driver.quit();
            driver = null;
        }
}
