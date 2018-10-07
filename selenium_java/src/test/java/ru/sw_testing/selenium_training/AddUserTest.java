package ru.sw_testing.selenium_training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

public class AddUserTest {

    private WebDriver driver;
    private WebDriverWait wait;

    public String getTestEmail(String lastName) {
        Random random = new Random();
        int num = random.nextInt(900);

        String email = lastName+num+"@mail.ru";
        return email;
    }

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void addUserTest() {
        String lastName = "Test";
        String email = getTestEmail(lastName);
        String password = "123456789";

        driver.get("http://localhost/litecart");

        // 1.Create account
        driver.findElement(By.cssSelector("div #box-account-login a")).click();
        driver.findElement(By.cssSelector("input[name=firstname]")).sendKeys("User");
        driver.findElement(By.cssSelector("input[name=lastname]")).sendKeys(lastName);
        driver.findElement(By.cssSelector("input[name=address1]")).sendKeys("street");
        WebElement selectElem = driver.findElement(By.cssSelector("select[name=country_code]"));
        Select countrySelect = new Select(selectElem);
        countrySelect.selectByVisibleText("United States");
        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name=postcode]")).sendKeys("55555");
        driver.findElement(By.cssSelector("input[name=city]")).sendKeys("City");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("123456789");
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("input[name=confirmed_password]")).sendKeys(password);
        driver.findElement(By.cssSelector("button[type=submit]")).click();

        // 2.Log out
        driver.findElement(By.cssSelector("a[href$=logout]")).click();

        // 3. Log in again
        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("button[type=submit]")).click();

        // 4. Log out again
        driver.findElement(By.cssSelector("a[href$=logout]")).click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
