package portal.moichor.com.pageObjects.home;

import lombok.var;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import portal.moichor.com.base.BasePage;

@Component
@Scope("cucumber-glue")
public class LoginPage extends BasePage {

  @FindBy(name = "email")
  public WebElement emailTextBox;

  @FindBy(name = "password")
  public WebElement passwordTextBox;

  @FindBy(css = "button[type='submit']")
  public WebElement loginButton;

  @FindBy(css = "label:nth-child(1) div[invalid='true']")
  public WebElement errorMessageForEmail;

  @FindBy(css = "label:nth-child(2) div[invalid='true']")
  public WebElement errorMessageForPassword;

  public LoginPage(WebDriver driver) {
    super(driver);
  }

  public void logInAs(String user) {

    var username = config.environment().getUser(user).username;
    var password = config.environment().getUser(user).password;
    if (config.environment().isActiveDirectoryUser) {
      logInAsStandardUser(username, password);
    }
  }

  private void setUsername(String username) {
    wait.waitForElementToBeVisible(emailTextBox);
    emailTextBox.sendKeys(username);
  }

  private void setPassword(String password) {
    wait.waitForElementToBeVisible(passwordTextBox);
    passwordTextBox.clear();
    passwordTextBox.sendKeys(password);
  }

  private void logInAsStandardUser(String username, String password) {
    wait.waitForElementToBeVisible(emailTextBox);
    setUsername(username);
    setPassword(password);
    loginButton.click();
  }

  public void logInAsAdminRootUser(String user) {
    driver.get("https://StageAdmin:AdminPass90253@stage.moichor.us/app/dashboard");
    var username = config.environment().getUser(user).username;
    var password = config.environment().getUser(user).password;
    if (config.environment().isActiveDirectoryUser) {
      logInAsStandardUser(username, password);
    }
  }

  public void logInAsSeleniumUser(String user) {
    var username = config.environment().getUser(user).username;
    var password = config.environment().getUser(user).password;
    if (config.environment().isActiveDirectoryUser) {
      logInAsStandardUser(username, password);
    }
  }
}
