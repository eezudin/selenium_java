package pageObjectStructure;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public abstract class BaseTest {
    public Application app;

    @BeforeTest
    public void start() {
         app = new Application();
    }


    @AfterTest
    public void stop() {

    }
}
