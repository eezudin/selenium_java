package ru.sw_testing.selenium_training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeoTest {

    private WebDriver driver;
    private WebDriverWait wait;

    public void checkAlphabet( List<WebElement> rows){
        List<String> beforeSort = new ArrayList<>();
        for (WebElement row : rows ) {
            WebElement link = row.findElement(By.cssSelector("a"));
            beforeSort.add(link.getText());
        }
        List<String> afterSort = new ArrayList<>();
        for (WebElement row : rows ) {
            WebElement link = row.findElement(By.cssSelector("a"));
            afterSort.add(link.getText());

        }
        Collections.sort(afterSort);
        assertTrue((beforeSort.equals(afterSort)));
    }

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
        //checkAlphabet(rows);
        for (WebElement row : rows){
            if(row.getAttribute("outerText").matches("\\t\\t\\d{1,3}\t[A-Z]{2}\t\\D*\t[1-9]{1,2}\\t")){
                System.out.println("true");}

            //\s*\d{1,3}\s[A-Z]{2}\D*[1-9]{1,2}
        }





    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
