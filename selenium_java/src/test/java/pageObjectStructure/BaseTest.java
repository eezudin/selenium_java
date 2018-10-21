package pageObjectStructure;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    public Application app;

    @BeforeTest
    public void start() {
         app = new Application();
    }

    @AfterTest
    public void stop() {
        app.quit();
       // driver = null;
    }
}
