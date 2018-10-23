package pageObjectStructure;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage{
    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void deleteProduct() {
        if (driver.findElements(By.cssSelector(".shortcut")).size() != 0) {
            driver.findElement(By.cssSelector(".shortcut")).click();
        }
        driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();
    }

    public boolean isItemDeleted(List<WebElement> items){
        String expectedText = "There are no items in your cart.";
        if(items.size() == 1){
            return wait.until(
                    ExpectedConditions.attributeContains(
                            By.cssSelector("div#checkout-cart-wrapper em"), "textContent", expectedText));
        }
        // wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("td.item"), items.size() - 1));
        return wait.until(d -> d.findElements(By.cssSelector("td.item")).size() == items.size() - 1);
    }

    public List<WebElement> getItems(){
        List<WebElement> items = driver.findElements(By.cssSelector("td.item"));
        return items;
    }
}
