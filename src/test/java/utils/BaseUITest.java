package utils;

import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import pages.StartPage;

public class BaseUITest {
    public AppiumDriver driver;
    public StartPage startPage;;

//    public AppiumDriver getDriver() { return driver; }
//    public StartPage getStartPage() { return startPage; }

    @BeforeEach
    public void setUp() throws Exception {
        this.driver = Common.getDriver();
        startPage = new StartPage(driver);
    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }

}
