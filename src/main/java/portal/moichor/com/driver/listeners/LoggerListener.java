package portal.moichor.com.driver.listeners;

import java.util.Arrays;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component("LoggerListener")
public class LoggerListener extends AbstractWebDriverEventListener {

    @Override
    public void beforeAlertAccept(WebDriver driver) {
        log.debug("Accepting alert");
    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {
        log.debug("Dismissing alert");
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        log.debug("Navigating to: " + url);
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        log.debug("Looking for element: " + by);
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        log.debug("Element " + by + " found");
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        log.debug("Clicking on element: " + element);
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        log.debug("Element: " + element + " has been clicked");
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        log.debug("Changing value to: " + Arrays.toString(keysToSend) + " in element: " + element);
    }

    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {
        log.debug("Getting text from element: " + element);
    }

    @Override
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {
        log.debug("Switching to window: " + windowName);
    }

}
