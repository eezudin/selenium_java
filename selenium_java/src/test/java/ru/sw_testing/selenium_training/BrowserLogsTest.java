package ru.sw_testing.selenium_training;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BrowserLogsTest {
    private EventFiringWebDriver driver;
    private WebDriverWait wait;

    public static class MyListener extends AbstractWebDriverEventListener {
        FileOutputStream fos;
        {
            try {
                fos = new FileOutputStream("C:\\Users\\Public\\Test_logs\\log.txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        PrintStream ps = new PrintStream(fos, false);

        public void out(LogEntry log) {
            String text = String.valueOf(log);
            System.out.println(text);
            ps.println(text);
        }

        public void stringOut(String text) {
            System.out.println(text);
            ps.println(text);
        }


        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            stringOut(LocalDateTime.now() + " " + by);
            driver.manage().logs().get("browser").forEach(l -> out(l));
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            stringOut(LocalDateTime.now() + " " + by + " found");
            driver.manage().logs().get("browser").forEach(l -> out(l));
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            stringOut(LocalDateTime.now() + " " + throwable);
            driver.manage().logs().get("browser").forEach(l -> out(l));
        }

    }

public boolean checkLogs() {
    try{
        FileInputStream stream = new FileInputStream("C:\\Users\\Public\\Test_logs\\log.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String strLine;
        while ((strLine = br.readLine()) != null){
            if(strLine.contains("SEVERE")) {
              return false;
            }
            if(strLine.contains("WARNING")) {
                return false;
            }
            if(strLine.contains("INFO")) {
                return false;
            }
        }
    }catch (IOException e){
        e.printStackTrace();
    }
    return true;

    }


    public void expandSubcategory() {
        List<WebElement> elements = driver.findElements(By.cssSelector("a[href*='category_id']"));
        for (WebElement element : elements) {
            if (element.getText().equals("Subcategory")) {
                element.click();
                break;
            }
        }
    }
    @BeforeTest
    public void start() {
        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.register(new MyListener());
        // System.out.println(driver.manage().logs().getAvailableLogTypes());
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void browserLogsTest() {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        expandSubcategory();

        List<WebElement> links = driver.findElements(By.cssSelector("a[href*='product_id']:not([title=Edit])"));
        List<String> ducks = new ArrayList<>();
        for (WebElement link : links){
                ducks.add(link.getText());
        }

        for (String duck : ducks) {
            List<WebElement> duckLinks = driver.findElements(By.cssSelector("a[href*='product_id']:not([title=Edit])"));
            for (WebElement duckLink : duckLinks){
                if (duckLink.getText().equals(duck)){
                    duckLink.click();
                    Assert.assertTrue(checkLogs());
                    driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
                    expandSubcategory();
                    break;
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
