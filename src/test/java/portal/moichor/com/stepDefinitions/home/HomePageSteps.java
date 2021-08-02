package portal.moichor.com.stepDefinitions.home;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import portal.moichor.com.base.BaseSteps;
import portal.moichor.com.pageObjects.adminPage.OrdersPage;
import portal.moichor.com.pageObjects.home.HomePage;
import portal.moichor.com.pageObjects.home.VeterinariansPage;
import portal.moichor.com.pageObjects.workflowPage.CreateNewTests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@Scope("cucumber-glue")
public class HomePageSteps extends BaseSteps {
  @Autowired HomePage homePage;
  @Autowired VeterinariansPage veterinariansPage;
  @Autowired OrdersPage ordersPage;

  @When("logout the application")
  public void logoutTheApplication() {
    wait.waitForElementToBeVisible(homePage.userName);
    homePage.userName.click();
    homePage.userDropDownList.get(1).click();
  }

  @Then("the page should redirect on login page")
  public void thePageShouldRedirectOnLoginPage() {
    Assert.assertTrue(driver.getCurrentUrl().contains("login"));
  }

  @When("Open the notification option")
  public void openTheNotificationOption() {
    wait.waitForElementToBeVisible(homePage.notificationsButton);
    homePage.notificationsButton.click();
    veterinariansPage.openTestId(CreateNewTests.Sample_id.toString());
  }

  @Then("the test status should displayed in {string}")
  public void theTestStatusShouldDisplayedIn(String testStatus) {
    wait.waitForElementToBeVisible(homePage.closeTwicePopUpWindow);
    homePage.closeTwicePopUpWindow.click();
    wait.waitForElementToBeVisible(ordersPage.testStatusBadge);
    assertThat(ordersPage.testStatusBadge.getText(), containsString(testStatus));
    ordersPage.closePopUp.click();
  }

  @Given("user logout the application")
  public void userLogoutTheApplication() {
    wait.waitForElementToBeVisible(homePage.userName);
    homePage.userName.click();
    homePage.signOut.click();
  }
}
