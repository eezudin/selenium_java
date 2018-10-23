package pageObjectStructure.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage {

    public MainPage(WebDriver driver) {
        super(driver);
    }
    public MainPage open() {
        driver.get("http://localhost/litecart");
        return this;
    }

    public void chooseFirstProduct() {
        driver.findElement(By.cssSelector("li[class^=product]")).click();
    }

    public void moveToCart() {
        driver.findElement(By.cssSelector("a.link[href$=checkout]")).click();
    }
}
