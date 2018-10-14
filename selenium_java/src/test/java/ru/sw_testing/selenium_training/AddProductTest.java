package ru.sw_testing.selenium_training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;

import java.util.List;

public class AddProductTest {
    private WebDriver driver;
    private WebDriverWait wait;


    public boolean checkNewProduct() {
        List<WebElement> products = driver.findElements(By.cssSelector("a[href*='product_id']"));
        for (WebElement elem : products) {
            if (elem.getAttribute("textContent").contains("Trump Duck")){
                return true;
            }
        }
        return false;
    }

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
        driver.manage().window().maximize();
    }

    @Test
    public void addProductTest() {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.findElement(By.cssSelector("a[href*='app=catalog&doc=catalog']")).click();
        driver.findElement(By.cssSelector("a[href*='edit_product']")).click();

        // Tab General
        driver.findElement(By.cssSelector("[type=radio][value='1']")).click();
        driver.findElement(By.cssSelector("input[name^=name]")).sendKeys("Trump Duck");
        driver.findElement(By.cssSelector("input[name=code]")).sendKeys("777");
        driver.findElement(By.cssSelector("input[data-name='Rubber Ducks")).click();
        Select defaultCategory = new Select(driver.findElement(By.cssSelector("select[name=default_category_id]")));
        defaultCategory.selectByVisibleText("Rubber Ducks");
        driver.findElement(By.cssSelector("input[value='1-3']")).click();
        driver.findElement(By.cssSelector("input[name=quantity]")).clear();
        driver.findElement(By.cssSelector("input[name=quantity]")).sendKeys("10");
        // driver.findElement(By.cssSelector("input[type=file]")).sendKeys("C:\\xampp\\htdocs\\litecart\\images\\trump_duck.jpg");
        File file = new File("trump_duck.jpg");
        driver.findElement(By.cssSelector("input[type=file]")).sendKeys(file.getAbsolutePath());
        driver.findElement(By.cssSelector("input[name=date_valid_from]")).sendKeys("01012018");
        driver.findElement(By.cssSelector("input[name=date_valid_to]")).sendKeys("31122019");

        // Tab Information
        wait.until(d -> d.findElement(By.cssSelector("a[href='#tab-information']"))).click();
        Select manufacturer = new Select(driver.findElement(By.cssSelector("select[name=manufacturer_id]")));
        manufacturer.selectByVisibleText("ACME Corp.");
        driver.findElement(By.cssSelector("input[name=keywords]")).sendKeys("Trump Duck");
        driver.findElement(By.cssSelector("input[name='short_description[en]']")).sendKeys("Trump Duck");
        driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("Trump Duck");
        driver.findElement(By.cssSelector("input[name='head_title[en]']")).sendKeys("TRUMP DUCK");

        // Tab Prices
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href='#tab-prices']"))).click();
        driver.findElement(By.cssSelector("input[name=purchase_price]")).clear();
        driver.findElement(By.cssSelector("input[name=purchase_price]")).sendKeys("12");
        Select purchasePriceCode = new Select(driver.findElement(By.cssSelector("select[name=purchase_price_currency_code]")));
        purchasePriceCode.selectByVisibleText("US Dollars");
        driver.findElement(By.cssSelector("input[name='prices[USD]']")).sendKeys("15");
        driver.findElement(By.cssSelector("input[name='prices[EUR]']")).sendKeys("14");
        driver.findElement(By.cssSelector("button[name=save]")).click();

        assert (checkNewProduct());
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
