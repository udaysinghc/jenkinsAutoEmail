package portal.moichor.com.pageObjects.tempEmail;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import portal.moichor.com.base.BasePage;

@Component
@Scope("cucumber-glue")
public class GetNadaTempMail extends BasePage {

  @FindBy(css = "input[placeholder*='Enter your inbox here']")
  public WebElement userName;

  @FindBy(css = "button[class='md']")
  public WebElement aeroButtonSubmit;

  @FindBy(css = "table a[href*='http']")
  public WebElement linkURl;
#
  @FindBy(id = "accept")
  WebElement cookiesPopUp;

  public GetNadaTempMail(WebDriver driver) {
    super(driver);
  }

  public void loginAsTempUser() throws InterruptedException {
    ((JavascriptExecutor) driver).executeScript("window.open()");
    switchToTab(1);
    driver.get("https://yopmail.com/en/");
    try {
      if (cookiesPopUp.isEnabled()) {
        cookiesPopUp.click();
      }
    } catch (NoSuchElementException ignore) {

    }

    wait.waitForElementToBeVisible(userName);
    userName.clear();
    userName.sendKeys("selenium");
    Thread.sleep(1000);
    aeroButtonSubmit.click();
    Thread.sleep(4000);
    driver.switchTo().frame("ifmail");
    Thread.sleep(2000);
    js.clickElement(linkURl);
    Thread.sleep(2000);
    driver.switchTo().defaultContent();
    driver.switchTo().parentFrame();
    Thread.sleep(2000);
    switchToTab(2);
    driver.getCurrentUrl();
    System.out.println("cuuentURl" + driver.getCurrentUrl());
    driver.get("https://stage.moichor.us/user/auth/login");
    wait.forPage();
  }
}
