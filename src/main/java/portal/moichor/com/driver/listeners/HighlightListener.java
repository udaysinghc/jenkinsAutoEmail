package portal.moichor.com.driver.listeners;


import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import portal.moichor.com.commons.JsExecutor;

@Log4j2
@Scope("cucumber-glue")
@Component("HighlightListener")
public class HighlightListener extends AbstractWebDriverEventListener {

    private static final String HIGHLIGHT_COLOR_AFTER = "#06d6a0";

    private JsExecutor jsExecutor;

    private JsExecutor getJsExecutor(WebDriver driver) {
        if (jsExecutor == null) {
            this.jsExecutor = new JsExecutor(driver);
        }
        return this.jsExecutor;
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        getJsExecutor(driver).highlightElement(element);
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        getJsExecutor(driver).highlightElement(element, HIGHLIGHT_COLOR_AFTER);
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        getJsExecutor(driver).highlightElement(element);
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        getJsExecutor(driver).highlightElement(element, HIGHLIGHT_COLOR_AFTER);
    }

    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {
        getJsExecutor(driver).highlightElement(element);
    }

    @Override
    public void afterGetText(WebElement element, WebDriver driver, String text) {
        getJsExecutor(driver).highlightElement(element, HIGHLIGHT_COLOR_AFTER);
    }

}
