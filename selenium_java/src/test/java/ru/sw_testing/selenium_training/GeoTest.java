package ru.sw_testing.selenium_training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeoTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void loginTest() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        List<WebElement> rows = driver.findElements(By.cssSelector("table.dataTable  tr.row"));
        List<String> countries = new ArrayList<>();
        for (WebElement row : rows ) {
            WebElement link = row.findElement(By.cssSelector("a"));
            countries.add(link.getText());
        }
        List<String> beforeSort = countries;

        System.out.println("BeforeSort");
        for (String a : beforeSort){
            System.out.println(a);
        }
        System.out.println("sort");
        Collections.sort(countries);

        System.out.println("After sort");
        for (String a : countries) {
            System.out.println(a);
        }


        System.out.println("Check");
            for (String a : countries){
                System.out.println(a);
            }


    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
