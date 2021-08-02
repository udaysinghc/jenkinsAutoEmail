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
import portal.moichor.com.pageObjects.home.ClientsPage;
import portal.moichor.com.pageObjects.home.HomePage;
import portal.moichor.com.pageObjects.home.VeterinariansPage;

@Scope("cucumber-glue")
public class ClientsPageSteps extends BaseSteps {
  @Autowired ClientsPage clientsPage;
  @Autowired HomePage homePage;
  @Autowired VeterinariansPage veterinariansPage;
  @Autowired ScenarioContext scenarioContext;
  public static Object clientFirstname;
  public static Object clientLastname;

  @And("Go to the Clients page")
  public void goToTheClientsPage() {
    wait.waitForElementToBeVisible(homePage.clientsOption);
    js.clickElement(homePage.clientsOption);
  }

  @When("create client firstName {string} lastname and email {string}")
  public void createClientFirstNameLastnameAndEmail(String firstName, String email) {
    wait.waitForElementToBeVisible(veterinariansPage.addNewButton);
    veterinariansPage.addNewButton.click();
    wait.waitForElementToBeVisible(veterinariansPage.firstNameTextBox);
    veterinariansPage.firstNameTextBox.sendKeys(firstName + homePage.currentDate());
    scenarioContext.setToContainer("Client_First_Name", veterinariansPage.getLatestVetFirstName());
    clientFirstname = scenarioContext.getFromContainer("Client_First_Name");
    veterinariansPage.lastNameTextBox.sendKeys(homePage.currentDate());
    scenarioContext.setToContainer("Client_Last_Name", veterinariansPage.getLatestVetLastName());
    clientLastname = scenarioContext.getFromContainer("Client_Last_Name");
    clientsPage.externalIdTextBox.click();
    clientsPage.externalIdTextBox.sendKeys(email);
    clientsPage.createButton.click();
  }

  @And("successfully added Clients toast message is displayed")
  public void successfullyAddedClientsToastMessageIsDisplayed() {
    wait.waitForElementToBeVisible(veterinariansPage.toastMessage);
    assertThat(veterinariansPage.toastMessage.getText(), containsString("Client created"));
  }

  @Then("added Clients is displayed")
  public void addedClientsIsDisplayed() throws InterruptedException {
    wait.waitForElementToBeVisible(clientsPage.dropdownButton);
    clientsPage.searchFilter.sendKeys(clientFirstname.toString());
    Thread.sleep(2000);
    veterinariansPage.open(clientFirstname.toString());
    wait.waitForElementToBeVisible(veterinariansPage.firstNameTextBox);
    wait.forPage(40);
    assertThat(
        veterinariansPage.firstNameTextBox.getAttribute("value"),
        containsString(clientFirstname.toString()));
    assertThat(
        veterinariansPage.lastNameTextBox.getAttribute("value"),
        containsString(clientLastname.toString()));
    clientsPage.externalIdTextBox.click();
    assertThat(
        clientsPage.externalIdTextBox.getAttribute("value"), containsString("moicher@yopmail.com"));
  }

  @When("edit the added Clients")
  public void editTheAddedClients() throws InterruptedException {
    wait.waitForElementToBeVisible(veterinariansPage.addNewButton);
    wait.forPage(20);
    wait.waitForElementToBeVisible(clientsPage.dropdownButton);
    clientsPage.searchFilter.sendKeys(clientFirstname.toString());
    Thread.sleep(2000);
    veterinariansPage.open(clientFirstname.toString());
    wait.waitForElementToBeVisible(veterinariansPage.firstNameTextBox);
    veterinariansPage.firstNameTextBox.click();
    Thread.sleep(500);
    veterinariansPage.firstNameTextBox.clear();
    Thread.sleep(500);
    veterinariansPage.firstNameTextBox.sendKeys(clientFirstname.toString());
    veterinariansPage.lastNameTextBox.clear();
    Thread.sleep(500);
    veterinariansPage.lastNameTextBox.sendKeys(clientLastname.toString());
    clientsPage.externalIdTextBox.clear();
    clientsPage.externalIdTextBox.sendKeys("moicher@yopmail.com");
    clientsPage.saveButton.click();
  }

  @And("success edit Clients toast message is displayed")
  public void successEditClientsToastMessageIsDisplayed() throws InterruptedException {
    wait.waitForElementToBeVisible(veterinariansPage.toastMessage);
    assertThat(veterinariansPage.toastMessage.getText(), containsString("Client edited"));
    Thread.sleep(2000);
  }

  @Then("edited Clients is displayed")
  public void editedClientsIsDisplayed() throws InterruptedException {
    clientsPage.searchFilter.sendKeys(clientFirstname.toString());
    Thread.sleep(2000);
    veterinariansPage.open(clientFirstname.toString());
    wait.waitForTextToBePresentInElementValue(clientsPage.externalIdTextBox,"moicher@yopmail.com");
    assertThat(
        clientsPage.externalIdTextBox.getAttribute("value"), containsString("moicher@yopmail.com"));
  }

  @When("delete the added Clients")
  public void deleteTheAddedClients() throws InterruptedException {
    wait.waitForElementToBeVisible(veterinariansPage.addNewButton);
    wait.waitForElementToBeVisible(clientsPage.dropdownButton);
    clientsPage.searchFilter.sendKeys(clientFirstname.toString());
    Thread.sleep(2000);
    veterinariansPage.openLatestDelete();
    wait.waitForElementToBeVisible(clientsPage.deleteButton);
    clientsPage.deleteButton.click();
  }

  @Then("success  Clients is deleted toast message is displayed")
  public void successClientsIsDeletedToastMessageIsDisplayed() {
    wait.waitForElementToBeVisible(clientsPage.alertDeleteToastMessage);
    assertThat(clientsPage.alertDeleteToastMessage.getText(), containsString("Client deleted"));
  }

  @When("enter the client firstName: {string}, lastName: {string}")
  public void enterTheClientFirstNameLastName(String firstName, String lastName) {
    wait.waitForElementToBeVisible(veterinariansPage.addNewButton);
    veterinariansPage.addNewButton.click();
    veterinariansPage.firstNameTextBox.sendKeys(firstName);
    veterinariansPage.lastNameTextBox.sendKeys(lastName);
    clientsPage.createButton.click();
  }

  @Then("validation message should appear for firstNameField: {string}, lastNameFiled: {string}")
  public void validationMessageShouldAppearForFirstNameFieldLastNameFiled(
      String firstNameField, String lastNameFiled) {
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
  }

  @When("enter the long characters in firstName: {string},lastName: {string}")
  public void enterTheLongCharactersInFirstNameLastName(String firstName, String lastName) {
    wait.waitForElementToBeVisible(veterinariansPage.addNewButton);
    veterinariansPage.addNewButton.click();
    veterinariansPage.firstNameTextBox.sendKeys(firstName);
    veterinariansPage.lastNameTextBox.sendKeys(lastName);
    clientsPage.createButton.click();
  }

  @Then("validation message will be displayed for long character {string}")
  public void validationMessageWillBeDisplayedForLongCharacter(String longCharacter) {
    if (longCharacter.trim().length() > 0) {
      Assert.assertTrue(
          clientsPage.alertMessageForLongerName20letters.getText().contains(longCharacter));
    }
  }

  @When("search for {string}")
  public void searchFor(String clientName) throws InterruptedException {
    wait.waitForElementToBeVisible(clientsPage.dropdownButton);
    clientsPage.searchClient(clientName);
    Thread.sleep(2000);
  }

  @And("enter name on search field {string}")
  public void enterNameOnSearchField(String searchClient) throws InterruptedException {
    clientsPage.searchFilter.sendKeys(searchClient);
    Thread.sleep(2000);
  }

  @Then("search client name {string}  will displayed in sequence")
  public void searchClientNameWillDisplayedInSequence(String searchedName) {
    for (WebElement clientRowList : veterinariansPage.ClientList) {
      if (clientRowList.getText().contains(searchedName)) {
        assertThat(clientRowList.getText(), containsString(searchedName));
        wait.setImplicitWait(10);
      }
    }
  }

  @When("open the client name")
  public void openTheClientName() throws InterruptedException {
    wait.waitForElementToBeVisible(veterinariansPage.addNewButton);
    wait.waitForElementToBeVisible(clientsPage.dropdownButton);
    clientsPage.searchFilter.sendKeys(clientFirstname.toString());
    Thread.sleep(2000);
    veterinariansPage.open(clientFirstname.toString());
  }

  @Then("patients tab is displayed")
  public void patientsTabIsDisplayed() {
    wait.waitForElementToBeVisible(clientsPage.patientsTab);
    softAssert.assertTrue(clientsPage.patientsTab.isDisplayed());
  }
}
