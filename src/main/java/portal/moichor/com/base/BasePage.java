package portal.moichor.com.base;

import java.util.LinkedList;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import portal.moichor.com.Config;
import portal.moichor.com.commons.JsExecutor;
import portal.moichor.com.wait.ExplicitWait;

@Log4j2
public abstract class BasePage {
  // todo: consider autowire these variables
  @Autowired protected Config config;
  @Autowired protected ExplicitWait wait;
  protected WebDriver driver;
  protected JsExecutor js;
  protected Actions actions;

  public BasePage(WebDriver driver) {
    this.driver = driver;
    this.js = new JsExecutor(driver);
    this.actions = new Actions(driver);
    PageFactory.initElements(driver, this);
  }

  public void click(By locator) {
    js.clickElement(locator);
  }

  public void click(WebElement element) {
    js.clickElement(element);
  }

  public void click(WebElement webElement, int timeOutInSeconds) {
    log.debug("Wait and click:" + webElement);
    wait.waitForElementToBeClickable(
        webElement, timeOutInSeconds, config.timeout().poolingInterval);
    js.highlightElement(webElement);
    js.clickElement(webElement);
  }

  public Actions clickByAction(WebElement element) {
    log.debug("Click by action: " + element);
    wait.waitForElementToBeVisible(element);
    js.highlightElement(element);
    actions.click(element).perform();
    return actions;
  }

  public Actions doubleClick(WebElement element) {
    log.debug("Double click on: " + element);
    wait.waitForElementToBeVisible(element);
    js.highlightElement(element);
    actions.moveToElement(element);
    actions.doubleClick().perform();
    return actions;
  }

  public Actions rightClick(WebElement element) {
    log.debug("Double click on: " + element);
    wait.waitForElementToBeVisible(element);
    js.highlightElement(element);
    actions.moveToElement(element);
    actions.contextClick().perform();
    return actions;
  }

  public Actions hoverOn(WebElement element) {
    log.debug("Hover on: " + element);
    wait.waitForElementToBeVisible(element);
    js.highlightElement(element);
    actions.moveToElement(element).perform();
    return actions;
  }

  public WebElement getElement(By locator) {
    log.info(locator);
    return driver.findElement(locator);
  }

  public void switchToTab(int index) {
    var handlers = new LinkedList<String>(driver.getWindowHandles());
    driver.switchTo().window(handlers.get(index));
  }

  public boolean isElementPresent(By by) {
    var isPresent = false;
    try {
      if (driver.findElements(by).size() > 0) {
        isPresent = true;
      }
    } catch (NoSuchElementException e) {
      isPresent = false;
    }
    return isPresent;
  }
}
