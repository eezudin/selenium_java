package pageObjectStructure.Application;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjectStructure.Pages.CartPage;
import pageObjectStructure.Pages.MainPage;
import pageObjectStructure.Pages.ProductPage;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class Application {

    private WebDriver driver;

    public MainPage mainPage;
    public ProductPage productPage;
    public CartPage cartPage;


    public Application() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        this.mainPage = new MainPage(driver);
        this.productPage = new ProductPage(driver);
        this.cartPage = new CartPage(driver);
    }

    public void performPurchase(int times) {
        for (int i = 0; i <times ; i++) {
            mainPage.open().chooseFirstProduct();
            productPage.addProductToCart().checkCartCountIncrease();
        }
    }

    public void openCart() {
        mainPage.moveToCart();
    }

    public void clearCart() {
        int items = cartPage.getItems().size();
        for (int i = 0; i < items; i++) {
            List<WebElement> currentItems = cartPage.getItems();
            cartPage.deleteProduct();
            assertTrue(cartPage.isItemDeleted(currentItems));
        }
    }
}
