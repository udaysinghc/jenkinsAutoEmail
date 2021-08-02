package portal.moichor.com.stepDefinitions.home;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import portal.moichor.com.base.BaseSteps;
import portal.moichor.com.commons.ScenarioContext;
import portal.moichor.com.pageObjects.home.HomePage;
import portal.moichor.com.pageObjects.home.VeterinariansPage;

@Scope("cucumber-glue")
public class VeterinariansPageSteps extends BaseSteps {
  @Autowired VeterinariansPage veterinariansPage;
  @Autowired HomePage homePage;
  @Autowired ScenarioContext scenarioContext;

  @And("Go to the Veterinarians page")
  public void goToTheVeterinariansPage() {
    wait.waitForElementToBeVisible(homePage.veterinariansOption);
    js.clickElement(homePage.veterinariansOption);
  }

  @When("Add the new Veterinarians")
  public void addTheNewVeterinarians() {
    wait.waitForElementToBeVisible(veterinariansPage.addNewButton);
    veterinariansPage.addNewButton.click();
    wait.waitForElementToBeVisible(veterinariansPage.firstNameTextBox);
    veterinariansPage.firstNameTextBox.sendKeys("Vet" + homePage.currentDate());
    scenarioContext.setToContainer(
        "Veterinarians_FirstName", veterinariansPage.getLatestVetFirstName());
    veterinariansPage.lastNameTextBox.sendKeys(homePage.currentDate());
    scenarioContext.setToContainer(
        "Veterinarians_LastName", veterinariansPage.getLatestVetLastName());
    veterinariansPage.emailTextBox.sendKeys("moicher@yopmail.com");
    veterinariansPage.createButton.click();
  }

  @And("success add Veterinarian toast message is displayed")
  public void successAddVeterinarianToastMessageIsDisplayed() {
    wait.waitForElementToBeVisible(veterinariansPage.toastMessage);
    assertThat(veterinariansPage.toastMessage.getText(), containsString("Vet created"));
  }

  @Then("added Veterinarians is displayed")
  public void addedVeterinariansIsDisplayed() {
    wait.forPage(20);
    js.scrollDown();
    wait.forPage(10);
    js.scrollDown();
    wait.forPage(10);
    js.scrollDown();
    wait.forPage(10);
    js.scrollDown();
    wait.forPage(10);
    Object latestVetName = scenarioContext.getFromContainer("Veterinarians_FirstName");
    veterinariansPage.open(latestVetName.toString());
    wait.waitForElementToBeVisible(veterinariansPage.firstNameTextBox);
    assertThat(
        veterinariansPage.firstNameTextBox.getAttribute("value"),
        containsString(latestVetName.toString()));
    Object latestVetLastName = scenarioContext.getFromContainer("Veterinarians_LastName");
    assertThat(
        veterinariansPage.lastNameTextBox.getAttribute("value"),
        containsString(latestVetLastName.toString()));
    assertThat(
        veterinariansPage.emailTextBox.getAttribute("value"), containsString("moicher@yopmail.com"));
  }

  @When("edit the added Veterinarians")
  public void editTheAddedVeterinarians()  {
    veterinariansPage.cancelButtonPopupWindow.click();
    Object latestVetFirstName = scenarioContext.getFromContainer("Veterinarians_FirstName");
    Object latestVetLastName = scenarioContext.getFromContainer("Veterinarians_LastName");
    veterinariansPage.open(latestVetFirstName.toString());
    wait.waitForElementToBeVisible(veterinariansPage.firstNameTextBox);
    veterinariansPage.firstNameTextBox.clear();
    veterinariansPage.firstNameTextBox.sendKeys(latestVetFirstName.toString());
    veterinariansPage.lastNameTextBox.clear();
    wait.forPage(20);
    veterinariansPage.lastNameTextBox.sendKeys(latestVetLastName.toString());
    wait.forPage(10);
    veterinariansPage.emailTextBox.clear();
    veterinariansPage.emailTextBox.sendKeys("moicher@yopmail.com");
    veterinariansPage.createButton.click();
  }

  @And("success edit Veterinarian toast message is displayed")
  public void successEditVeterinarianToastMessageIsDisplayed() {
    wait.waitForElementToBeVisible(veterinariansPage.toastMessage);
    assertThat(veterinariansPage.toastMessage.getText(), containsString("Vet edited"));
  }

  @Then("edit Veterinarians is displayed")
  public void editVeterinariansIsDisplayed() {
    Object latestVetFirstName = scenarioContext.getFromContainer("Veterinarians_FirstName");
    veterinariansPage.open(latestVetFirstName.toString());
    wait.waitForElementToBeVisible(veterinariansPage.firstNameTextBox);
    assertThat(
        veterinariansPage.firstNameTextBox.getAttribute("value"),
        containsString(latestVetFirstName.toString()));
  }

  @When("click on close icon button")
  public void clickOnCloseIconButton() {
    veterinariansPage.addNewButton.click();
    veterinariansPage.closeIconButtonPopupWindow.click();
  }

  @Then("Create Veterinarian popup window will be closed")
  public void createVeterinarianPopupWindowWillBeClosed() {
    Assert.assertFalse(veterinariansPage.titleOFfVeterinarianPopup.isDisplayed());
  }

  @When("click on cancel button")
  public void clickOnCancelButton() {
    wait.waitForElementToBeVisible(veterinariansPage.addNewButton);
    veterinariansPage.addNewButton.click();
    veterinariansPage.cancelButtonPopupWindow.click();
  }

  @When("enter the firstName: {string}, lastName: {string}, email: {string}")
  public void enterTheFirstNameLastNameEmail(String firstName, String lastName, String email) {
    wait.waitForElementToBeVisible(veterinariansPage.addNewButton);
    veterinariansPage.addNewButton.click();
    veterinariansPage.firstNameTextBox.sendKeys(firstName);
    veterinariansPage.lastNameTextBox.sendKeys(lastName);
    veterinariansPage.emailTextBox.sendKeys(email);
    veterinariansPage.createButton.click();
  }

  @Then(
      "validation message should appear for firstNameField: {string}, lastNameFiled: {string}, emailField {string}")
  public void validationMessageShouldAppearForFirstNameFieldLastNameFiledEmailField(
      String firstNameField, String lastNameFiled, String emailFiled) {

    if (veterinariansPage.errorMessageForTextField.size() > 0) {
      for (WebElement firstName : veterinariansPage.errorMessageForTextField) {
        if (firstName.getText().contains(firstNameField)) {
          assertThat(firstName.getText(), containsString(firstNameField));
          wait.setImplicitWait(10);
        }
      }
    }
    if (veterinariansPage.errorMessageForTextField.size() > 0) {
      for (WebElement lastName : veterinariansPage.errorMessageForTextField) {
        if (lastName.getText().contains(lastNameFiled)) {
          assertThat(lastName.getText(), containsString(lastNameFiled));
          wait.setImplicitWait(10);
        }
      }
    }
    if (veterinariansPage.errorMessageForTextField.size() > 0) {
      for (WebElement email : veterinariansPage.errorMessageForTextField) {
        if (email.getText().contains(emailFiled)) {
          assertThat(email.getText(), containsString(emailFiled));
          wait.setImplicitWait(10);
        }
      }
    }
  }
}
