package ru.sw_testing.selenium_training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class AdminMenuTest {

    private WebDriver driver;
    private WebDriverWait wait;

    public void checkHeader(){
        driver.findElement(By.cssSelector("h1"));
    }

    @Before
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

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
