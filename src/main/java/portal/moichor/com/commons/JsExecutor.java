package portal.moichor.com.commons;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import portal.moichor.com.Config;

@Component
@Scope("cucumber-glue")
@Log4j2
public class JsExecutor {

  private static final String HIGHLIGHT_COLOR = "#ebda42";

  @Autowired private Config config;
  private WebDriver driver;
  private JavascriptExecutor js;

  @Autowired
  public JsExecutor(WebDriver driver) {
    this.driver = driver;
    this.js = (JavascriptExecutor) driver;
  }

  public Object executeScript(String script) {
    log.debug(script);
    return js.executeScript(script);
  }

  public Object executeScript(String script, Object... args) {
    log.debug(script);
    return js.executeScript(script, args);
  }

  public void scrollToElement(WebElement element) {
    log.debug("Scroll to element " + element);
    executeScript(
        "window.scrollTo(arguments[0],arguments[1])",
        element.getLocation().x,
        element.getLocation().y);
  }

  public void scrollToElement(By locator) {
    log.debug("Scroll to element " + locator);
    scrollToElement(driver.findElement(locator));
  }

  public void scrollIntoView(WebElement element) {
    log.debug("Scroll into view " + element);
    executeScript("arguments[0].scrollIntoView(true)", element);
  }

  public void scrollIntoView(By locator) {
    log.debug("Scroll into view " + locator);
    scrollIntoView(driver.findElement(locator));
  }

  public void clickElement(By locator) {
    clickElement(driver.findElement(locator));
  }

  public void clickElement(WebElement element) {
    log.debug("Click element by js " + element);
    js.executeScript("arguments[0].click()", element);
  }

  public void scrollDown() {
    scrollDown(1000);
  }

  public void scrollDown(int numberOfPixels) {
    log.debug("Scroll down: " + numberOfPixels);
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(0," + numberOfPixels + ")");
  }

  public void scrollUp() {
    scrollUp(200);
  }

  public void scrollUp(int numberOfPixels) {
    log.debug("Scroll up: " + numberOfPixels);
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(0,-" + numberOfPixels + ")");
  }

  public void highlightElement(By locator) {
    highlightElement(driver.findElement(locator));
  }

  public void highlightElement(WebElement element) {
    highlightElement(element, HIGHLIGHT_COLOR);
  }

  public void highlightElement(WebElement element, String color) {
    log.debug("Highlight element: " + element);

    try {
      setAttribute(element, "style", "border: 4px solid " + color);
    } catch (Exception exception) {
      log.debug("Exception occurred while highlighting element: " + exception);
    }
  }

  public void setAttribute(WebElement element, String attributeName, String attributeValue) {
    log.debug("Set attribute: " + attributeName + attributeValue);
    executeScript(
        "arguments[0].setAttribute('" + attributeName + "', arguments[1]); ",
        element,
        attributeValue + ";");
  }

  public void setZoomLevel(double zoomLevel) {
    log.debug("Set zoom level: " + zoomLevel);
    executeScript("document.body.style.zoom = '" + zoomLevel + "'");
  }
}
