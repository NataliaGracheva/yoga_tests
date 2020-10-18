package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class StartPage {
    private AppiumDriver driver;

    private By welcome = By.xpath("//*[contains(@text, 'Добро пожаловать!')]");
    private By startBtn = By.xpath("//*[contains(@text, 'НАЧАТЬ')]");

    public StartPage(AppiumDriver driver) {
        this.driver = driver;
    }

    public void checkHeader() {
        Common.waitForElementPresent(driver, welcome, "Нет заголовка", 10);
    }

    public UserPreferences clickStart() {
        Common.waitForElementPresent(driver, welcome, "Нет заголовка", 10);
        Common.waitForElement(driver, startBtn, "Нет кнопки Начать", 3).click();

        return new UserPreferences(driver);
    }

}
