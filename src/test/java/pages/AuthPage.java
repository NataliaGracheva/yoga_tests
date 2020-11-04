package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import utils.Common;

public class AuthPage {
    private AppiumDriver driver;

    private By header = By.xpath("//*[contains(@text, 'Зарегистрируйтесь и получите бесплатный доступ')]");

    public AuthPage(AppiumDriver driver) {
        this.driver = driver;
    }

    public void checkHeader() {
        Common.waitForElementPresent(driver, header, "Нет заголовка", 5);
    }
}
