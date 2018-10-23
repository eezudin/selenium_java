package pageObjectStructure.Tests;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import pageObjectStructure.Application.Application;

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
