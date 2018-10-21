package pageObjectStructure;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Application {

    private WebDriver driver;
    private WebDriverWait wait;

    public Application() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }

    public void quit() {
        driver.quit();
    }

    public List<WebElement> getItems() {
        List<WebElement> items = driver.findElements(By.cssSelector("td.item"));
        return items;
    }
    public void openMainPage() {
        driver.get("http://localhost/litecart");
    }
    public void deleteProduct() {
        if (driver.findElements(By.cssSelector(".shortcut")).size() != 0) {
            driver.findElement(By.cssSelector(".shortcut")).click();
        }
        driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();
    }

    public void checkCartCountIncrease() {
        WebElement cartCountElement = driver.findElement(By.cssSelector("span.quantity"));
        int cartCount = Integer.parseInt(cartCountElement.getText());
        String expectedCartCount = String.valueOf(cartCount + 1);
        wait.until(ExpectedConditions.attributeContains(By.cssSelector("span.quantity"), "textContent", expectedCartCount ));
    }
    public void moveToCart() {
        driver.findElement(By.cssSelector("a.link[href$=checkout]")).click();
    }

    public void chooseFirstProduct() {
        driver.findElement(By.cssSelector("li[class^=product]")).click();
    }

    public void addProductToCart(){
        if(driver.findElements(By.cssSelector("select[name='options[Size]']")).size() != 0){
            Select duckSizeSelect = new Select(driver.findElement(By.cssSelector("select[name='options[Size]']")));
            duckSizeSelect.selectByVisibleText("Small");
        }
        driver.findElement(By.cssSelector("button[name=add_cart_product]")).click();
    }

    public boolean isItemDeleted(){
        List<WebElement> items = driver.findElements(By.cssSelector("td.item"));
        String expectedText = "There are no items in your cart.";
        if(items.size() == 1){
            return wait.until(
                    ExpectedConditions.attributeContains(
                            By.cssSelector("div#checkout-cart-wrapper em"), "textContent", expectedText));
        }
        // wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("td.item"), items.size() - 1));
        return wait.until(d -> d.findElements(By.cssSelector("td.item")).size() == items.size() - 1);
    }



}
