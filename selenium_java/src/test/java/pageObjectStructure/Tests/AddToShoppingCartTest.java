package pageObjectStructure.Tests;

import org.testng.annotations.Test;

public class AddToShoppingCartTest extends BaseTest {

    @Test
    public void addToShoppingCartTest() {
        app.performPurchase(3);
        app.openCart();
        app.clearCart();
    }
}
