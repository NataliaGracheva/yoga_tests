package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import utils.Common;

public class UserPreferences {
    private AppiumDriver driver;

    private By backBtn = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.RelativeLayout[3]/android.widget.LinearLayout[1]/android.widget.ImageView");
    private By skipBtn = By.xpath("//*[contains(@text, 'Пропустить')]");
    private By selectBtn = By.xpath("//*[contains(@text, 'ВЫБРАТЬ')]");

    private String options_xpath = "//android.widget.TextView[@text='{substring}']";
    private String headers_xpath = "//android.widget.TextView[contains(@text, '{substring}')]";

    String experienceHeader = "Какой у Вас опыт в йоге?";
    String instructionsHeader = "Какой объем инструкций вы хотите получить?";
    String musicHeader = "Теперь немного мелодий";
    String yogaTypeHeader = "Выберите вид йоги";
    String paceHeader = "Темп";
    String focusOnHeader = "Boost";
    String shavasanaTimeHeader = "Шавасана";
    String practiceTimeHeader = "Выберите продолжительность вашего первого занятия";


    public UserPreferences(AppiumDriver driver) {
        this.driver = driver;
    }

    public void selectOption(String header, String option) {
        String header_xpath = headers_xpath.replace("{substring}", header);
        String option_xpath = options_xpath.replace("{substring}", option);
        Common.getScreenshot(driver);
        Common.waitForElementPresent(driver, By.xpath(header_xpath), "Нет заголовка: " + header, 10);
        Common.scrollToElementAndClick(driver, By.xpath(option_xpath), "Пункта нет в списке: " + option, 3);
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

    public void skipOption() {
//        Common.waitForElementPresent(driver, skipBtn, "Нет кнопки Пропустить", 5);
        Common.waitForElement(driver, skipBtn, "Нет кнопки Пропустить", 5).click();
    }

    public AuthPage skipAllOptionsWithTakingScreenshots(int num) {
        for (int i = 0; i < num; i++) {
            Common.getScreenshot(driver);
            skipOption();
        }

        return new AuthPage(driver);
    }

    public void goBack(String currentText, String expectedText) {
        Common.waitForElementPresent(driver, By.xpath("//*[contains(@text, '" + currentText + "')]"), "Нет текущего заголовка: " + currentText, 10);
        Common.waitForElement(driver, backBtn, "Нет кнопки Назад", 3).click();
        Common.waitForElementPresent(driver, By.xpath("//*[contains(@text, '" + expectedText + "')]"), "Нет ожидаемого заголовка: " + expectedText, 10);
    }

}
