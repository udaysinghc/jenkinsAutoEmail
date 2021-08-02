package portal.moichor.com.pageObjects.workflowPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import portal.moichor.com.base.BasePage;
import portal.moichor.com.commons.ScenarioContext;
import portal.moichor.com.pageObjects.adminPage.OrdersPage;
import portal.moichor.com.pageObjects.home.ClientsPage;
import portal.moichor.com.pageObjects.home.HomePage;
import portal.moichor.com.pageObjects.home.PatientPage;
import portal.moichor.com.pageObjects.home.TestsPage;
import portal.moichor.com.pageObjects.home.VeterinariansPage;

@Component
@Scope("cucumber-glue")
public class CreateNewTests extends BasePage {
  @Autowired HomePage homePage;
  @Autowired VeterinariansPage veterinariansPage;
  @Autowired PatientPage patientPage;
  @Autowired TestsPage testsPage;
  @Autowired ClientsPage clientsPage;
  @Autowired OrdersPage ordersPage;
  @Autowired ScenarioContext scenarioContext;
  public static Object veterinarianFirstName;
  public static Object clientFirstname;
  public static Object patientName;
  public static Object Sample_id;

  public CreateNewTests(WebDriver driver) {
    super(driver);
  }

  public void createNewVet() throws InterruptedException {
    wait.waitForElementToBeVisible(homePage.veterinariansOption);
    js.clickElement(homePage.veterinariansOption);
    wait.waitForElementToBeVisible(veterinariansPage.addNewButton);
    veterinariansPage.addNewButton.click();
    wait.waitForElementToBeVisible(veterinariansPage.firstNameTextBox);
    veterinariansPage.firstNameTextBox.sendKeys("Vet" + homePage.currentDate());
    scenarioContext.setToContainer(
        "Veterinarians_FirstName", veterinariansPage.getLatestVetFirstName());
    veterinarianFirstName = scenarioContext.getFromContainer("Veterinarians_FirstName");
    veterinariansPage.lastNameTextBox.sendKeys(homePage.currentDate());
    veterinariansPage.emailTextBox.sendKeys("moicher@yopmail.com");
    veterinariansPage.createButton.click();
    wait.waitForElementToBeVisible(veterinariansPage.toastMessage);
    assertThat(veterinariansPage.toastMessage.getText(), containsString("Vet created"));
    Thread.sleep(3000);
  }

  public void createNewClient() throws InterruptedException {
    wait.waitForElementToBeVisible(homePage.clientsOption);
    js.clickElement(homePage.clientsOption);
    wait.waitForElementToBeClickable(veterinariansPage.addNewButton);
    veterinariansPage.addNewButton.click();
    wait.waitForElementToBeVisible(veterinariansPage.firstNameTextBox);
    veterinariansPage.firstNameTextBox.sendKeys("Client" + homePage.currentDate());
    scenarioContext.setToContainer("Client_First_Name", veterinariansPage.getLatestVetFirstName());
    clientFirstname = scenarioContext.getFromContainer("Client_First_Name");
    veterinariansPage.lastNameTextBox.sendKeys(homePage.currentDate());
    clientsPage.externalIdTextBox.click();
    clientsPage.externalIdTextBox.sendKeys("moicher@yopmail.com");
    clientsPage.createButton.click();
    wait.waitForElementToBeVisible(veterinariansPage.toastMessage);
    assertThat(veterinariansPage.toastMessage.getText(), containsString("Client created"));
    Thread.sleep(1000);
  }

  public void createNewPatient() throws InterruptedException {
    driver.navigate().refresh();
    wait.waitForElementToBeVisible(patientPage.patientOption);
    js.clickElement(patientPage.patientOption);
    wait.waitForElementToBeClickable(veterinariansPage.addNewButton);
    veterinariansPage.addNewButton.click();
    wait.waitForElementToBeVisible(patientPage.patientNameTextBox);
    patientPage.patientNameTextBox.sendKeys("Patient" + homePage.currentDate());
    scenarioContext.setToContainer("Patient_Name", patientPage.getLatestPatientName());
    patientName = scenarioContext.getFromContainer("Patient_Name");
    patientPage.DOBTextBox.sendKeys("10-12-2010");
    patientPage.dropDownOption.get(0).click();
    patientPage.selectSex("Male");
    Thread.sleep(1000);
    try {
      patientPage.clientsSearchTextBox.click();
      clientFirstname = scenarioContext.getFromContainer("Client_First_Name");
      Thread.sleep(1000);
      for (WebElement selectClientName : patientPage.listOfClient) {
        if (selectClientName.getText().contains(clientFirstname.toString())) {
          selectClientName.click();
        }
      }
    } catch (StaleElementReferenceException ignored) {
    }
    try {
      patientPage.speciesSearchTextBox.click();
      patientPage.speciesSearchTextBox.sendKeys("White");
      Thread.sleep(1000);
      for (WebElement spicesList : patientPage.listOfClient) {
        if (spicesList.getText().contains("White")) {
          spicesList.click();
        }
      }
    } catch (StaleElementReferenceException ignored) {
    }
    patientPage.weightTextBox.sendKeys("40");
    clientsPage.createButton.click();
    wait.waitForElementToBeVisible(veterinariansPage.toastMessage);
    assertThat(veterinariansPage.toastMessage.getText(), containsString("Patient created"));
    Thread.sleep(2000);
  }

  public void createNewTests() throws InterruptedException {
    wait.waitForElementToBeVisible(testsPage.testsOption);
    js.clickElement(testsPage.testsOption);
    wait.waitForElementToBeVisible(veterinariansPage.addNewButton);
    veterinariansPage.addNewButton.click();
    wait.waitForElementToBeVisible(testsPage.placeholder);
    testsPage.placeholder.click();
    testsPage.sendInputValue.get(0).sendKeys(clientFirstname.toString());
    wait.forPage(20);
    try {
      for (WebElement selectClientName : patientPage.listOfClient) {
        if (selectClientName.getText().contains(clientFirstname.toString())) {
          selectClientName.click();
        }
      }
    } catch (StaleElementReferenceException ignored) {

    }

    try {
      wait.waitForElementToBeVisible(testsPage.placeholder);
      testsPage.placeholder.click();
      testsPage.sendInputValue.get(1).sendKeys(patientName.toString());
      wait.forPage(20);
      for (WebElement selectPatient : patientPage.listOfClient) {
        if (selectPatient.getText().contains(patientName.toString())) {
          selectPatient.click();
        }
      }
    } catch (StaleElementReferenceException ignored) {

    }
    js.scrollToElement(testsPage.sampleId);
    testsPage.serviceMenuButton.get(0).click();
    wait.waitForElementToBeVisible(testsPage.titleOfTestAppliesText);
    testsPage.checkBoxForTestApplies.get(3).click();
    testsPage.applyAndCloseButton.click();
    wait.waitForElementToBeVisible(testsPage.sampleId);
    testsPage.serviceMenuButton.get(1).click();
    Thread.sleep(500);
    scenarioContext.setToContainer("Sample_Id", testsPage.getLatestSampleId());
    Sample_id = scenarioContext.getFromContainer("Sample_Id");
    Thread.sleep(1000);
    testsPage.setFeedBackNumber();
    wait.waitForElementToBeVisible(testsPage.popupVetText);
    testsPage.placeholder.click();
    Thread.sleep(500);
    testsPage.sendInputValue.get(4).sendKeys(veterinarianFirstName.toString());
    Thread.sleep(2000);
    try {
      for (WebElement selectVetName : patientPage.listOfClient) {
        if (selectVetName.getText().contains(veterinarianFirstName.toString())) {
          selectVetName.click();
          Thread.sleep(1000);
        }
      }
    } catch (StaleElementReferenceException ignored) {

    }
    js.scrollToElement(testsPage.selectUncheckedBox);
    testsPage.selectUncheckedBox.click();
    veterinariansPage.createButton.click();
    Thread.sleep(1000);
    wait.waitForElementToBeVisible(veterinariansPage.toastMessage);
    assertThat(veterinariansPage.toastMessage.getText(), containsString("Test created"));
    Thread.sleep(3000);
  }

  public void navigateToOrderPage() {
    wait.waitForElementToBeVisible(ordersPage.ordersOption);
    ordersPage.ordersOption.click();
  }

  public void deleteAssignPatient() throws InterruptedException {
    wait.waitForElementToBeVisible(patientPage.patientOption);
    js.clickElement(patientPage.patientOption);
    wait.waitForElementToBeVisible(patientPage.searchFilter);
    patientPage.searchFilter.click();
    patientPage.searchFilter.sendKeys(patientName.toString());
    Thread.sleep(1000);
    veterinariansPage.open(patientName.toString());
    wait.waitForElementToBeVisible(patientPage.deleteButton);
    patientPage.deleteButton.click();
    wait.waitForElementToBeVisible(patientPage.deleteButtonPopUP);
    patientPage.deleteButtonPopUP.click();
    wait.waitForElementToBeVisible(ordersPage.alertMessageToast);
    assertThat(
        ordersPage.alertMessageToast.getText(),
        containsString("Patient has test. Could not be deleted"));
    Thread.sleep(3000);
  }
}
