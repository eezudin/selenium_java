package ru.sw_testing.selenium_training;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class ShoppingCartTest {

    private WebDriver driver;
    private WebDriverWait wait;

    public void addItemWaitCountIncrease(){
        WebElement cartCountElement = driver.findElement(By.cssSelector("span.quantity"));
        int cartCount = Integer.parseInt(cartCountElement.getText());
        String expectedCartCount = String.valueOf(cartCount + 1);
        if(driver.findElements(By.cssSelector("select[name='options[Size]']")).size() != 0){
            Select duckSizeSelect = new Select(driver.findElement(By.cssSelector("select[name='options[Size]']")));
            duckSizeSelect.selectByVisibleText("Small");
        }
        driver.findElement(By.cssSelector("button[name=add_cart_product]")).click();
        wait.until(ExpectedConditions.attributeContains(By.cssSelector("span.quantity"), "textContent", expectedCartCount ));
    }

    public boolean isItemDeleted(List<WebElement> items, WebDriver driver){
        String expectedText = "There are no items in your cart.";
        if(items.size() == 1){
            return wait.until(
                    ExpectedConditions.attributeContains(
                            By.cssSelector("div#checkout-cart-wrapper em"), "textContent", expectedText));
        }
        // wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("td.item"), items.size() - 1));
        return wait.until(d -> d.findElements(By.cssSelector("td.item")).size() == items.size() - 1);
    }

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void shoppingCartTest() {
        for (int i = 0; i < 3; i++) {
            driver.get("http://localhost/litecart");
            driver.findElement(By.cssSelector("li[class^=product]")).click();
            addItemWaitCountIncrease();
        }

        driver.findElement(By.cssSelector("a.link[href$=checkout]")).click();
        List<WebElement> items = driver.findElements(By.cssSelector("td.item"));
            for (int i = 0; i < items.size(); i++) {
                List<WebElement> currentItems = driver.findElements(By.cssSelector("td.item"));
                if (driver.findElements(By.cssSelector(".shortcut")).size() != 0){
                    driver.findElement(By.cssSelector(".shortcut")).click();
                }
                driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();
                assertTrue(isItemDeleted(currentItems, driver));
                }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
