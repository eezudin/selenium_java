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
import java.util.List;

public class StickerMainPageTest {

    private WebDriver driver;
    private WebDriverWait wait;

    public boolean checkSticker(WebElement element){
        if (element.findElements(By.cssSelector("div.sticker")).size() == 1){
            return true;
        }
        return false;
    }

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);

    }

    @Test
    public void stikerMainPageTest() {

        List <String> blocks= new ArrayList<>();
        blocks.add("div#box-most-popular li");
        blocks.add("div#box-campaigns li");
        blocks.add("div#box-latest-products li");


        driver.get("http://localhost/litecart");
        for (String block : blocks) {
            List<WebElement> elements = driver.findElements(By.cssSelector(block));
            int amountOfItems = elements.size();

            for (int i = 1; i <= amountOfItems; i++) {
                WebElement duck = driver.findElement(By.cssSelector(block+":nth-child(" + i + ")"));
                assert (checkSticker(duck));
            }
        }
    }
    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
