package portal.moichor.com.wait;



import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import portal.moichor.com.Config;
import portal.moichor.com.commons.JsExecutor;

@Log4j2
@Component
@Scope("cucumber-glue")
public class ExplicitWait {

    @Autowired
    private JsExecutor js;
    @Autowired
    private WebDriver driver;
    @Autowired
    private Config config;

    public ExplicitWait() {
    }

    private WebDriverWait getWait() {
        return getWait(config.timeout().explicit, config.timeout().poolingInterval);
    }

    private WebDriverWait getWait(int timeOutInSeconds, int pollingEveryInMiliSec) {
        var wait = new WebDriverWait(driver, timeOutInSeconds, pollingEveryInMiliSec);
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(ElementNotVisibleException.class);
        wait.ignoring(StaleElementReferenceException.class);
        wait.ignoring(NoSuchFrameException.class);
        return wait;
    }

    public void setImplicitWait(long timeout, TimeUnit unit) {
        log.info("Implicit timeout is set to: " + timeout);
        driver.manage().timeouts().implicitlyWait(timeout, unit);
    }

    public void setImplicitWait(long timeout) {
        setImplicitWait(timeout, TimeUnit.SECONDS);
    }

    public WebElement waitForElementToBePresent(By locator) {
        log.info("Waiting for element to be present: " + locator);
        return waitFor(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitForElementToBeVisible(By locator) {
        return waitForElementToBeVisible(locator, config.timeout().explicit, config.timeout().poolingInterval);
    }

    public WebElement waitForElementToBeVisible(By locator, int timeoutInSeconds) {
        return waitForElementToBeVisible(locator, timeoutInSeconds, config.timeout().poolingInterval);
    }

    public WebElement waitForElementToBeVisible(WebElement webElement) {
        return waitForElementToBeVisible(webElement, config.timeout().explicit, config.timeout().poolingInterval);
    }

    public WebElement waitForElementToBeVisible(WebElement webElement, int timeoutInSeconds) {
        return waitForElementToBeVisible(webElement, timeoutInSeconds, config.timeout().poolingInterval);
    }

    public WebElement waitForElementToBeVisible(By locator, int timeOutInSeconds, int pollingEveryInMiliSec) {
        log.info("Waiting for locator to be visible: " + locator);
        forPage();
        return waitFor(ExpectedConditions.visibilityOfElementLocated(locator), timeOutInSeconds, pollingEveryInMiliSec);
    }

    public WebElement waitForElementToBeVisible(WebElement webElement, int timeOutInSeconds, int pollingEveryInMiliSec) {
        log.info("Waiting for element to be visible: " + webElement);
        forPage();
        return waitFor(ExpectedConditions.visibilityOf(webElement), timeOutInSeconds, pollingEveryInMiliSec);
    }

    public boolean waitForElementContainsText(By locator, String searchedText) {
        log.info("Waiting for element: " + locator + " to contains text: " + searchedText);
        forPage();
        waitForElementExistAndVisible(locator, config.timeout().explicit, config.timeout().poolingInterval);
        return waitFor(textExistInElement(driver.findElement(locator), searchedText));
    }

    public boolean waitForTextToBePresentInElementValue(WebElement element, String searchedText) {
        log.info("Waiting for text " + searchedText + " to be present in element: " + element);
        return waitFor(ExpectedConditions.textToBePresentInElementValue(element, searchedText));
    }

    public WebElement waitForElementToBeClickable(By element) {
        return waitForElementToBeClickable(element, config.timeout().explicit, config.timeout().poolingInterval);
    }

    public WebElement waitForElementToBeClickable(By element, int timeoutInSeconds) {
        return waitForElementToBeClickable(element, timeoutInSeconds, config.timeout().poolingInterval);
    }

    public WebElement waitForElementToBeClickable(WebElement element) {
        return waitForElementToBeClickable(element, config.timeout().explicit, config.timeout().poolingInterval);
    }

    public WebElement waitForElementToBeClickable(WebElement element, int timeoutInSeconds) {
        return waitForElementToBeClickable(element, timeoutInSeconds, config.timeout().poolingInterval);
    }

    public WebElement waitForElementToBeClickable(WebElement element, int timeOutInSeconds, int pollingEveryInMiliSec) {
        log.info("Waiting for element to be clickable: " + element);
        forPage();
        return waitFor(ExpectedConditions.elementToBeClickable(element), timeOutInSeconds, pollingEveryInMiliSec);
    }

    public WebElement waitForElementToBeClickable(By element, int timeOutInSeconds, int pollingEveryInMiliSec) {
        log.info("Waiting for element to be clickable: " + element);
        forPage();
        return waitFor(ExpectedConditions.elementToBeClickable(element), timeOutInSeconds, pollingEveryInMiliSec);
    }

    public boolean waitForInvisibilityOfElement(WebElement element) {
        return waitForInvisibilityOfElement(element, config.timeout().explicit, config.timeout().poolingInterval);
    }

    public boolean waitForInvisibilityOfElement(By locator) {
        return waitForInvisibilityOfElement(locator, config.timeout().explicit, config.timeout().poolingInterval);
    }

    public boolean waitForInvisibilityOfElement(WebElement element, int timeOutInSeconds) {
        return waitForInvisibilityOfElement(element, timeOutInSeconds, config.timeout().poolingInterval);
    }

    public boolean waitForInvisibilityOfElement(WebElement element, int timeOutInSeconds, int pollingEveryInMiliSec) {
        log.info("Waiting for invisibility of element: " + element);
        return waitFor(ExpectedConditions.invisibilityOf(element), timeOutInSeconds, pollingEveryInMiliSec);
    }

    public boolean waitForInvisibilityOfElement(By locator, int timeOutInSeconds, int pollingEveryInMiliSec) {
        log.info("Waiting for invisibility of element: " + locator);
        return waitFor(ExpectedConditions.invisibilityOfElementLocated(locator), timeOutInSeconds, pollingEveryInMiliSec);
    }

    public boolean forPage() {
        return forPage(config.timeout().page);
    }

    public boolean forPage(int timeoutInSeconds) {
        log.debug("Waiting for the document to load");
        WebDriverWait wait = getWait(timeoutInSeconds, config.timeout().poolingInterval);
        waitForInvisibilityOfElement(By.cssSelector(".react-select__loading-indicator"),
                config.timeout().page, config.timeout().poolingInterval);
        waitForInvisibilityOfElement(By.cssSelector(".react-select__menu-notice--loading"),
                config.timeout().page, config.timeout().poolingInterval);
        return wait.until(jQueryLoad()) && wait.until(documentLoad());
    }

    public boolean isElementExist(By locator, int timeOutInSeconds) {
        WebDriverWait wait = getWait(timeOutInSeconds, config.timeout().poolingInterval);
        return wait.until(elementIsPresentInDOM(locator));
    }

    public WebElement waitForElementExistAndVisible(By locator, int timeOutInSeconds, int pollingEveryInMiliSec) {
        log.info("Wait for element exist and visible: " + locator);
        forPage();
        waitFor(elementIsPresentInDOM(locator), timeOutInSeconds, pollingEveryInMiliSec);
        js.scrollIntoView(locator);
        return (WebElement) waitFor(ExpectedConditions.visibilityOfElementLocated(locator),
                timeOutInSeconds, pollingEveryInMiliSec);
    }

    public void waitForIframe(By locator, int timeOutInSeconds, int pollingEveryInMiliSec) {
        log.info(locator);
        waitFor(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator),
                timeOutInSeconds, pollingEveryInMiliSec);
        driver.switchTo().defaultContent();
    }

    public void waitUntil(Function expectedCondition) {
        getWait().until(expectedCondition);
    }

    public boolean waitForNewTab(int timeOutInSeconds, int pollingEveryInMiliSec) {
        log.info("Wait for new Tab to be open");
        return waitFor(ExpectedConditions.numberOfWindowsToBe(2),
                timeOutInSeconds, pollingEveryInMiliSec);
    }

    private <V> V waitFor(Function expectedCondition) {
        return waitFor(expectedCondition, config.timeout().explicit, config.timeout().poolingInterval);
    }

    private <V> V waitFor(Function expectedCondition, int timeOutInSeconds, int pollingEveryInMiliSec) {
        setImplicitWait(0);
        WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec);
        V result = (V) wait.until(expectedCondition);
        setImplicitWait(config.timeout().implicit);
        return result;
    }

    //todo: I changed guava functions to java functions
    //not tested, there could be issues
    private Function<WebDriver, Boolean> textExistInElement(WebElement element, String searchedText) {
        return new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                String textFromElement = element.getText();
                if (textFromElement.contains(searchedText)) {
                    return true;
                }
                return false;
            }
        };
    }

    private Function<WebDriver, Boolean> elementIsPresentInDOM(final By locator) {
        return new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.findElements(locator).size() > 0;
            }
        };
    }

    private Function<WebDriver, Boolean> jQueryLoad() {
        return new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) js.executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };
    }

    private Function<WebDriver, Boolean> documentLoad() {
        return new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return js.executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };
    }

}
