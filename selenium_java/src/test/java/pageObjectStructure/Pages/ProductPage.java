package pageObjectStructure.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage addProductToCart(){
        if(driver.findElements(By.cssSelector("select[name='options[Size]']")).size() != 0){
            Select duckSizeSelect = new Select(driver.findElement(By.cssSelector("select[name='options[Size]']")));
            duckSizeSelect.selectByVisibleText("Small");
        }
        driver.findElement(By.cssSelector("button[name=add_cart_product]")).click();
        return this;
    }

    public void checkCartCountIncrease() {
        WebElement cartCountElement = driver.findElement(By.cssSelector("span.quantity"));
        int cartCount = Integer.parseInt(cartCountElement.getText());
        String expectedCartCount = String.valueOf(cartCount + 1);
        wait.until(ExpectedConditions.attributeContains(By.cssSelector("span.quantity"), "textContent", expectedCartCount ));
    }
}
