package pageObjectStructure;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
