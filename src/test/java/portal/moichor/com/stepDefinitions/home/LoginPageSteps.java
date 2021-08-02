package portal.moichor.com.stepDefinitions.home;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import portal.moichor.com.base.BaseSteps;
import portal.moichor.com.pageObjects.home.LoginPage;

@Scope("cucumber-glue")
public class LoginPageSteps extends BaseSteps {
  @Autowired public LoginPage loginPage;

  @Given("Log in as client root {string} user")
  public void logInAsClientRootUser(String user) {
    loginPage.logInAs(user);
  }

  @When("enter email: {string} and password: {string}")
  public void enterEmailAndPassword(String email, String password) {
    loginPage.emailTextBox.sendKeys(email);
    loginPage.passwordTextBox.sendKeys(password);
    loginPage.loginButton.click();
  }

  @Then("validation message should appear for email: {string} and {string}")
  public void validationMessageShouldAppearForEmailAnd(String emailFiled, String passwordFiled) {
    if (emailFiled.trim().length()> 0) {
      Assert.assertTrue(loginPage.errorMessageForEmail.getText().contains(emailFiled));
      wait.setImplicitWait(10);
    }
    if (passwordFiled.trim().length() > 0) {
      Assert.assertTrue(loginPage.errorMessageForPassword.getText().contains(passwordFiled));
      wait.setImplicitWait(10);
    }
  }
  @Given("Log in as admin root {string} user")
  public void logInAsAdminRootUser(String user) {
  loginPage.logInAsAdminRootUser(user);
    }

    @Given("log in as selenium root {string} user")
    public void logInAsSeleniumRootUser(String user) {
      loginPage.logInAsSeleniumUser(user);
    }
}
