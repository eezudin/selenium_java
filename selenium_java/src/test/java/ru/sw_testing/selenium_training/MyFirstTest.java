package ru.sw_testing.selenium_training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyFirstTest {

  /*  WebDriver chromeDriver = new ChromeDriver();
      WebDriver ieDriver = new InternetExplorerDriver();
      WebDriver firefoxDriver = new FirefoxDriver(); */

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        //System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void myFirstTest() {
        driver.get("http://www.google.com/ncr");
        driver.findElement(By.name("q")).sendKeys("webdriver");
        //not stable
        //driver.findElement(By.name("btnK")).click();
        driver.findElement(By.name("btnK")).sendKeys(Keys.ENTER);
        wait.until(titleIs("webdriver - Google Search"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
