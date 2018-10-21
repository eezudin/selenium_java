package ru.sw_testing.selenium_training;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;


public class AdminMenuTest {

    private WebDriver driver;
    private WebDriverWait wait;

    public void checkHeader(){
        driver.findElement(By.cssSelector("h1"));
    }

    @BeforeTest
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void adminMenuTest() {
        driver.get("http://localhost/litecart/admin");
        driver.manage().window().maximize();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        List<WebElement> elements = driver.findElements(By.cssSelector("ul#box-apps-menu li#app-"));
        int amountOfElements = elements.size();

        for (int i = 1; i <=amountOfElements ; i++) {
            driver.findElement(By.cssSelector("ul#box-apps-menu > li:nth-child(" +i+ ")")).click();
            checkHeader();
            List<WebElement> SubElements = driver.findElements(By.cssSelector("ul#box-apps-menu ul.docs li"));
            int amountOfSubElements = SubElements.size();

            if (amountOfSubElements != 0){
                for (int j = 1; j <=amountOfSubElements ; j++) {
                    driver.findElement(By.cssSelector("#box-apps-menu ul.docs > li:nth-child(" +j+ ")")).click();
                    checkHeader();
                }
            }
        }
    }

    @AfterTest
    public void stop() {
        driver.quit();
        driver = null;
    }
}
