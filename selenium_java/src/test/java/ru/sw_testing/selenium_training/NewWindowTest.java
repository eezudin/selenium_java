package ru.sw_testing.selenium_training;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

public class NewWindowTest {
    private WebDriver driver;
    private WebDriverWait wait;

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

    public boolean checkNewWindows() {
        List<WebElement> elements = driver.findElements(By.xpath("//i[@class='fa fa-external-link']"));
        if (elements.size() != 0) {
            for (int i = 0; i < elements.size(); i++) {
                String originalWindow = driver.getWindowHandle();
                Set<String> existingWindows = driver.getWindowHandles();
                driver.findElement(By.xpath("//i[@class='fa fa-external-link']")).click();
                String newWindow = wait.until(anyWindowOtherThan(existingWindows));
                driver.switchTo().window(newWindow);
                driver.close();
                driver.switchTo().window(originalWindow);
            }
            return true;
        }
        return false;
    }

    @BeforeTest
    public void start() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void newWindowTest() {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.findElement(By.xpath("//a[contains(@href,'countries')]")).click();
        driver.findElement(By.xpath("//a[@title='Edit']")).click();
        Assert.assertTrue(checkNewWindows());

    }

    @AfterTest
    public void stop() {
        driver.quit();
        driver = null;
    }
}
