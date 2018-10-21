package pageObjectStructure;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;


public class AddToShoppingCartTest extends BaseTest {

        @Test
        public void addToShoppingCartTest() {
            for (int i = 0; i < 3; i++) {
                app.openMainPage();
                app.chooseFirstProduct();
                app.addProductToCart();
                app.checkCartCountIncrease();
            }
            app.moveToCart();
            ;
            for (int i = 0; i < app.getItems().size(); i++) {
                app.deleteProduct();
                assertTrue(app.isItemDeleted());
            }

        }
}
