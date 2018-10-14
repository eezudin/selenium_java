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
import java.util.concurrent.TimeUnit;

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
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            if(items.size() == 1){
                String expectedText = "There are no items in your cart.";
                return driver.findElement(By.cssSelector("div#checkout-cart-wrapper em")).getText().equals(expectedText);
            }
            return driver.findElements(By.cssSelector("td.item")).size() == items.size() - 1 ;
        } finally {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        }
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
        System.out.println("");
        /*if(driver.findElements(By.cssSelector("ul.shortcuts")) != null){
            driver.findElement(By.cssSelector("ul.shortcuts")).click();
        }*/
        //wait.until(ExpectedConditions.  By.cssSelector("button[name=remove_cart_item")));
        List<WebElement> items = driver.findElements(By.cssSelector("td.item"));//driver.findElements(By.cssSelector("button[name=remove_cart_item]"));
            for (int i = 0; i < items.size(); i++) {
                System.out.println("");
                List<WebElement> currentItems = driver.findElements(By.cssSelector("td.item"));
                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[name=remove_cart_item]")));
                driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();
                assertTrue(isItemDeleted(currentItems, driver));
                }



        System.out.println();



    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
