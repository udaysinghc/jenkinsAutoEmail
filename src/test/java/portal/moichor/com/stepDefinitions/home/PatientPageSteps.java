package portal.moichor.com.stepDefinitions.home;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import portal.moichor.com.base.BaseSteps;
import portal.moichor.com.commons.ScenarioContext;
import portal.moichor.com.pageObjects.home.ClientsPage;
import portal.moichor.com.pageObjects.home.HomePage;
import portal.moichor.com.pageObjects.home.PatientPage;
import portal.moichor.com.pageObjects.home.VeterinariansPage;

@Scope("cucumber-glue")
public class PatientPageSteps extends BaseSteps {
  @Autowired ClientsPage clientsPage;
  @Autowired HomePage homePage;
  @Autowired VeterinariansPage veterinariansPage;
  @Autowired PatientPage patientPage;
  @Autowired ScenarioContext scenarioContext;
  public static Object patientName;

  @And("Go to the patient page")
  public void goToThePatientPage() {
    wait.waitForElementToBeVisible(patientPage.patientOption);
    js.clickElement(patientPage.patientOption);
  }

  @When(
      "add patient name: {string}, dateOfBirth: {string}, sex: {string}, weight: {string}, species: {string}")
  public void addPatientNameDateOfBirthSexWeightSpecies(
      String name, String dateOfBirth, String sex, String weight, String species)
      throws InterruptedException {
    driver.navigate().refresh();
    wait.forPage(10);
    wait.waitForElementToBeVisible(patientPage.patientOption);
    js.clickElement(patientPage.patientOption);
    wait.waitForElementToBeVisible(veterinariansPage.addNewButton);
    veterinariansPage.addNewButton.click();
    wait.waitForElementToBeVisible(patientPage.patientNameTextBox);
    patientPage.patientNameTextBox.sendKeys(name + homePage.currentDate());
    scenarioContext.setToContainer("Patient_Name", patientPage.getLatestPatientName());
    patientName = scenarioContext.getFromContainer("Patient_Name");
    patientPage.DOBTextBox.sendKeys(dateOfBirth);
    patientPage.dropDownOption.get(0).click();
    patientPage.selectSex(sex);
    Thread.sleep(1000);
    try {
      patientPage.clientsSearchTextBox.click();
      for (WebElement selectClientName : patientPage.listOfClient) {
        if (selectClientName.getText().contains(ClientsPageSteps.clientFirstname.toString())) {
          selectClientName.click();
        }
      }
    } catch (StaleElementReferenceException ignored) {
    }
    try {
      patientPage.speciesSearchTextBox.click();
      patientPage.speciesSearchTextBox.sendKeys(species);
      Thread.sleep(1000);
      for (WebElement spicesList : patientPage.listOfClient) {

        if (spicesList.getText().contains("White")) {
          spicesList.click();
        }
      }
    } catch (StaleElementReferenceException ignored) {
    }
    patientPage.weightTextBox.sendKeys(weight);
    clientsPage.createButton.click();
  }

  @Then("added Patient is displayed")
  public void addedPatientIsDisplayed() throws InterruptedException {
    wait.waitForElementToBeVisible(clientsPage.dropdownButton);
    clientsPage.searchFilter.sendKeys(patientName.toString());
    Thread.sleep(2000);
    veterinariansPage.open(patientName.toString());
    wait.waitForElementToBeVisible(patientPage.patientNameTextBox);
    assertThat(
        patientPage.patientNameTextBox.getAttribute("value"),
        containsString(patientName.toString()));
  }

  @When("delete the added Patient")
  public void deleteTheAddedPatient() throws InterruptedException {
    wait.waitForElementToBeVisible(veterinariansPage.addNewButton);
    wait.waitForElementToBeVisible(clientsPage.dropdownButton);
    clientsPage.searchFilter.sendKeys(patientName.toString());
    Thread.sleep(2000);
    veterinariansPage.openLatestDelete();
    wait.waitForElementToBeVisible(clientsPage.deleteButton);
    clientsPage.deleteButton.click();
  }

  @Then("successfully Patient is deleted toast message is displayed")
  public void successfullyPatientIsDeletedToastMessageIsDisplayed() {
    wait.waitForElementToBeVisible(patientPage.deleteSuccessToastMessage);
    assertThat(patientPage.deleteSuccessToastMessage.getText(), containsString("Patient deleted"));
  }

  @When("enter patient name: {string}, client: {string}, species: {string}")
  public void enterPatientNameClientSpecies(String patientName, String client, String species)
      throws InterruptedException {
    driver.navigate().refresh();
    wait.waitForElementToBeVisible(patientPage.patientOption);
    js.clickElement(patientPage.patientOption);
    wait.waitForElementToBeVisible(veterinariansPage.addNewButton);
    veterinariansPage.addNewButton.click();
    wait.waitForElementToBeVisible(patientPage.patientNameTextBox);
    patientPage.patientNameTextBox.sendKeys(patientName);
    try {
      if (client.trim().length() > 0) {
        patientPage.clientsSearchTextBox.click();
        wait.forPage(20);
        patientPage.clientsSearchTextBox.sendKeys("Test");
        Thread.sleep(1000);
        for (WebElement listOfClient : patientPage.listOfClient) {

          if (listOfClient.getText().contains("Test Test")) {
            listOfClient.click();
          }
        }
      }
    } catch (StaleElementReferenceException ignored) {
    }
    try {
      if (species.trim().length() > 0) {
        patientPage.speciesSearchTextBox.click();
        patientPage.speciesSearchTextBox.sendKeys(species);
        Thread.sleep(1000);
        for (WebElement spicesList : patientPage.listOfClient) {

          if (spicesList.getText().contains("White")) {
            spicesList.click();
          }
        }
      }
    } catch (StaleElementReferenceException ignored) {
    }

    clientsPage.createButton.click();
  }

  @And("successfully added Patient toast message is displayed")
  public void successfullyAddedPatientToastMessageIsDisplayed() throws InterruptedException {
    wait.waitForElementToBeVisible(veterinariansPage.toastMessage);
    assertThat(veterinariansPage.toastMessage.getText(), containsString("Patient created"));
    Thread.sleep(1000);
  }

  @Then(
      "validation error message should appear for name: {string}, client: {string}, species {string}")
  public void validationErrorMessageShouldAppearForNameClientSpecies(
      String patientNameFiled, String clientNameFiled, String spicesNameFiled) {
    if (veterinariansPage.errorMessageForTextField.size() > 0) {
      for (WebElement firstName : veterinariansPage.errorMessageForTextField) {
        if (firstName.getText().contains(patientNameFiled)) {
          assertThat(firstName.getText(), containsString(patientNameFiled));
          wait.setImplicitWait(10);
        }
      }
    }
    if (patientPage.tooltipMessage.size() > 0) {
      for (WebElement clientTooltip : patientPage.tooltipMessage) {
        if (clientTooltip.getText().contains(clientNameFiled)) {
          assertThat(clientTooltip.getText(), containsString(clientNameFiled));
          wait.setImplicitWait(10);
        }
      }
    }
    if (patientPage.tooltipMessage.size() > 0) {
      for (WebElement spiceTooltip : patientPage.tooltipMessage) {
        if (spiceTooltip.getText().contains(spicesNameFiled)) {
          assertThat(spiceTooltip.getText(), containsString(spicesNameFiled));
          wait.setImplicitWait(10);
        }
      }
    }
  }

  @When("enter patient name: {string}")
  public void enterPatientName(String patientName) throws InterruptedException {
    wait.waitForElementToBeVisible(veterinariansPage.addNewButton);
    veterinariansPage.addNewButton.click();
    wait.waitForElementToBeVisible(patientPage.patientNameTextBox);
    patientPage.patientNameTextBox.sendKeys(patientName);
    patientPage.selectSex.sendKeys("Male");
    new Actions(driver).sendKeys(Keys.ENTER).build().perform();
    Thread.sleep(2000);
    try {
      patientPage.clientsSearchTextBox.click();
      wait.forPage(20);
      patientPage.clientsSearchTextBox.sendKeys("Test");
      Thread.sleep(1000);
      for (WebElement listOfClient : patientPage.listOfClient) {
        if (listOfClient.getText().contains("Test Test")) {
          listOfClient.click();
        }
      }
    } catch (StaleElementReferenceException ignored) {

    }
    try {
      patientPage.speciesSearchTextBox.click();
      wait.forPage(20);
      patientPage.speciesSearchTextBox.sendKeys("White");
      Thread.sleep(1000);
      for (WebElement spicesList : patientPage.listOfClient) {

        if (spicesList.getText().contains("White")) {
          spicesList.click();
        }
      }
    } catch (StaleElementReferenceException ignored) {
    }
    clientsPage.createButton.click();
  }

  @Then("validation error message should appear for patient name filed: {string}")
  public void validationErrorMessageShouldAppearForPatientNameFiled(String longCharacter) {
    if (longCharacter.trim().length() > 0) {
      Assert.assertTrue(
          clientsPage.alertMessageForLongerName20letters.getText().contains(longCharacter));
    }
  }

  @Then("successfully added tests toast message is displayed")
  public void successfullyAddedTestsToastMessageIsDisplayed() {
    wait.waitForElementToBeVisible(veterinariansPage.toastMessage);
    assertThat(veterinariansPage.toastMessage.getText(), containsString("Test created"));
  }
}
