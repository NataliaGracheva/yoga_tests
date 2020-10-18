package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class UserPreferences {
    private AppiumDriver driver;

    private By experienceHeader = By.xpath("//*[contains(@text, 'Какой у Вас опыт в йоге?')]");
    private By instructionsHeader = By.xpath("//*[contains(@text, 'Какой объем инструкций вы хотите получить?')]");
    private By musicHeader = By.xpath("//*[contains(@text, 'Теперь немного мелодий')]");
    private By yogaTypeHeader = By.xpath("//*[contains(@text, 'Выберите вид йоги')]");
    private By paceHeader = By.xpath("//*[contains(@text, 'Темп')]");
    private By focusOnHeader = By.xpath("//*[contains(@text, 'Boost')]");
    private By shavasanaTimeHeader = By.xpath("//*[contains(@text, 'Шавасана')]");
    private By practiceTimeHeader = By.xpath("//*[contains(@text, 'Выберите продолжительность вашего первого занятия')]");

    private By backBtn = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.RelativeLayout[3]/android.widget.LinearLayout[1]/android.widget.ImageView");
    private By skipBtn = By.xpath("//*[contains(@text, 'Пропустить')]");
    private By selectBtn = By.xpath("//*[contains(@text, 'ВЫБРАТЬ')]");

//    private String options_xpath = "//*[contains(@text, '{substring}')]";
    private String options_xpath = "//*[@text='{substring}']";


    public UserPreferences(AppiumDriver driver) {
        this.driver = driver;
    }

    public void selectOption(By header, String option) {
        String xPath = options_xpath.replace("{substring}", option);
        Common.waitForElementPresent(driver, header, "Нет заголовка", 10);
        Common.scrollToElementAndClick(driver, By.xpath(xPath), "Пункта нет в списке: " + option, 3);
        Common.waitForElement(driver, selectBtn, "Нет кнопки Выбрать", 3).click();
    }

    public AuthPage setPreferences(String experience, String instructions, String music, String yogaType,
                                   String pace, String focusOn, String shavasanaTime, String practiceTime) {

        selectOption(experienceHeader, experience);
        selectOption(instructionsHeader, instructions);
        selectOption(musicHeader, music);
        selectOption(yogaTypeHeader, yogaType);
        selectOption(paceHeader, pace);
        selectOption(focusOnHeader, focusOn);
        selectOption(shavasanaTimeHeader, shavasanaTime);
        selectOption(practiceTimeHeader, practiceTime);

        return new AuthPage(driver);
    }

//    public void setExperienceOption(String experience) {
//        selectOption(experienceHeader, experience);
//    }

    public void skipOption() {
        Common.waitForElementPresent(driver, skipBtn, "Нет кнопки Пропустить", 5);
        Common.waitForElement(driver, skipBtn, "Нет кнопки Пропустить", 3).click();
    }

    public AuthPage skipAllOptionsWithTakingScreenshots(int num) {
        for (int i = 0; i < num; i++) {
            Common.getScreenshot(driver);
            skipOption();
        }

        return new AuthPage(driver);
    }

    public void goBack(String currentText, String expectedText) {
        Common.waitForElementPresent(driver, By.xpath("//*[contains(@text, '" + currentText + "')]"), "Нет текущего заголовка", 10);
        Common.waitForElement(driver, backBtn, "Нет кнопки Назад", 3).click();
        Common.waitForElementPresent(driver, By.xpath("//*[contains(@text, '" + expectedText + "')]"), "Нет следующего заголовка", 10);
    }

}
