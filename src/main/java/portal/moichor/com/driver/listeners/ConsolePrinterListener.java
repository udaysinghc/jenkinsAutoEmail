package portal.moichor.com.driver.listeners;


import java.util.Arrays;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import portal.moichor.com.logger.ConsolePrinter;
import portal.moichor.com.logger.options.Color;

@Log4j2
@Component("ConsolePrinterListener")
public class ConsolePrinterListener extends AbstractWebDriverEventListener {

    @Autowired
    ConsolePrinter printer;

    @Override
    public void beforeAlertAccept(WebDriver driver) {
        printer.color(Color.PURPLE).
                print("Accepting alert").reset();
    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {
        printer.color(Color.PURPLE)
                .print("Dismissing alert").reset();
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        printer.color(Color.PURPLE).print("Navigating to: " + url).reset();
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        printer.color(Color.PURPLE).print("Looking for element: " + by).reset();
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        printer.color(Color.PURPLE).print("Element " + by + " found").reset();
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        printer.color(Color.PURPLE).print("Clicking on element: " + element).reset();
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        printer.color(Color.PURPLE).print("Element: " + element + " has been clicked").reset();
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        printer.color(Color.PURPLE).print("Changing value to: " + Arrays.toString(keysToSend)
                + " in element: " + element).reset();
    }

    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {
        printer.color(Color.PURPLE).print("Getting text from element: " + element).reset();
    }

    @Override
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {
        printer.color(Color.PURPLE).print("Switching to window: " + windowName).reset();
    }
}
