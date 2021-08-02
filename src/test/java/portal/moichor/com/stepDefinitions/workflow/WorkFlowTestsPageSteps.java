package portal.moichor.com.stepDefinitions.workflow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import portal.moichor.com.base.BaseSteps;
import portal.moichor.com.pageObjects.adminPage.OrdersPage;
import portal.moichor.com.pageObjects.home.HomePage;
import portal.moichor.com.pageObjects.home.TestsPage;
import portal.moichor.com.pageObjects.home.VeterinariansPage;
import portal.moichor.com.pageObjects.workflowPage.CreateNewTests;

@Scope("cucumber-glue")
public class WorkFlowTestsPageSteps extends BaseSteps {
  @Autowired CreateNewTests createNewTests;
  @Autowired VeterinariansPage veterinariansPage;
  @Autowired TestsPage testsPage;
  @Autowired OrdersPage ordersPage;
  @Autowired HomePage homePage;
  public static String filePath =
      System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\moichor\\";

  @And("create the new tests list")
  public void createTheNewTestsList() throws InterruptedException {
    createNewTests.createNewVet();
    createNewTests.createNewClient();
    createNewTests.createNewPatient();
    createNewTests.createNewTests();
    createNewTests.deleteAssignPatient();
  }

  @When("change status latest tests result to pending on admin side")
  public void changeStatusLatestTestsResultToPendingOnAdminSide() throws InterruptedException {
    createNewTests.navigateToOrderPage();
    testsPage.openTestId(CreateNewTests.Sample_id.toString());
    ordersPage.inputOpenCase.sendKeys("Selenium automation");
    Thread.sleep(2000);
    new Actions(driver).sendKeys(Keys.ENTER).build().perform();
    ordersPage.senReviewButton.click();
    Thread.sleep(1000);
    ordersPage.pendingButton.click();
    wait.waitForElementToBeVisible(testsPage.testStatusPopupText);
    softAssert.assertTrue(testsPage.testStatusPopupText.isDisplayed());
    wait.waitForElementToBeVisible(testsPage.historicalTests);
    testsPage.historicalTests.click();
    for (WebElement listTestHistory : testsPage.testHistory) {
      if (listTestHistory.getText().contains(CreateNewTests.Sample_id.toString())) {
        assertThat(listTestHistory.getText(), containsString(CreateNewTests.Sample_id.toString()));
      }
    }
    ordersPage.closeButtonPopUpWindow.get(1).click();
    testsPage.addNote.click();
    testsPage.addNote.sendKeys("Test pending");
    testsPage.saveNoteButton.click();
    softAssert.assertEquals(
        veterinariansPage.toastMessage.getText(), containsString("Test's note updated."));
    wait.waitForElementToBeClickable(testsPage.changeBillingStatusButton);
    testsPage.changeBillingStatusButton.click();
    testsPage.selectPaidCheckBox.click();
    testsPage.updateButton.click();
    testsPage.yesButton.click();
    wait.waitForElementToBeClickable(ordersPage.closeButtonPopUpWindow.get(0));
    ordersPage.closeButtonPopUpWindow.get(0).click();
  }

  @Then("latest test status should be changed on pending")
  public void latestTestStatusShouldBeChangedOnPending() {
    driver.navigate().refresh();
    wait.forPage();
    wait.waitForElementToBeVisible(testsPage.badges.get(0));
    softAssert.assertEquals(testsPage.badges.get(0).getText(), containsString("pending"));
  }

  @When("Open the latest notification option")
  public void openTheLatestNotificationOption() {
    wait.waitForElementToBeVisible(homePage.notificationsButton);
    homePage.notificationsButton.click();
    wait.waitForElementToBeVisible(testsPage.rowList.get(0));
    testsPage.openTesGo("Go to test");
  }

  @Then("the latest test status  should displayed in {string}")
  public void theLatestTestStatusShouldDisplayedIn(String status) {
    wait.waitForElementToBeVisible(testsPage.pendingTest);
    assertThat(testsPage.pendingTest.getText(), containsString(status.toUpperCase()));
  }

  @And("change the latest  status of tests is completed")
  public void changeTheLatestStatusOfTestsIsCompleted() throws InterruptedException {
    createNewTests.navigateToOrderPage();
    testsPage.openTestId(CreateNewTests.Sample_id.toString());
    wait.waitForElementToBeVisible(ordersPage.manualReviewButton);
    if (testsPage.clinicalCloseButton.isDisplayed()) {
      js.clickElement(testsPage.clinicalCloseButton);
    }
    ordersPage.inputOpenCase.sendKeys("Selenium automation");
    Thread.sleep(2000);
    new Actions(driver).sendKeys(Keys.ENTER).build().perform();
    ordersPage.senReviewButton.click();
    Thread.sleep(1000);
    testsPage.completed.click();
    ordersPage.closeButtonPopUpWindow.get(0).click();
    driver.navigate().refresh();
    createNewTests.navigateToOrderPage();
    wait.waitForElementToBeVisible(testsPage.badges.get(0));
    softAssert.assertEquals(testsPage.badges.get(0).getText(), containsString("completed"));
  }

  @When("change the completed test status to Request pathology review")
  public void changeTheCompletedTestStatusToRequestPathologyReview() {
    wait.waitForElementToBeVisible(homePage.notificationsButton);
    homePage.notificationsButton.click();
    testsPage.openTesGo("Go to test");
    wait.waitForElementToBeVisible(testsPage.requestPathologyReviewButton);
    testsPage.requestPathologyReviewButton.click();
    wait.waitForElementToBeVisible(testsPage.textareaPathology);
    testsPage.textareaPathology.click();
    testsPage.textareaPathology.sendKeys("a sample test is good");
    testsPage.submitButton.click();
    wait.waitForElementToBeVisible(ordersPage.alertMessageToast);
    assertThat(
        ordersPage.alertMessageToast.getText(),
        containsString("Request has been sent successfully!"));
  }

  @Then("admin user will see the status of tests is {string}")
  public void adminUserWillSeeTheStatusOfTestsIs(String pathologyReview) {
    wait.waitForElementToBeVisible(homePage.notificationsButton);
    homePage.notificationsButton.click();
    wait.waitForElementToBeVisible(testsPage.rowList.get(0));
    testsPage.openTesGo("Go to test");
    wait.waitForElementToBeVisible(ordersPage.closeButtonPopUpWindow.get(1));
    ordersPage.closeButtonPopUpWindow.get(1).click();
    wait.waitForElementToBeVisible(testsPage.pathologyStatusOnPopUpWindow);
    assertThat(testsPage.pathologyStatusOnPopUpWindow.getText(), containsString(pathologyReview));
    ordersPage.closeButtonPopUpWindow.get(0).click();
    driver.navigate().refresh();
    wait.waitForElementToBeVisible(testsPage.badges.get(0));
    for (WebElement listStatus : testsPage.pathologyReviewStatus)
      if (listStatus.getText().contains("Pathology Review")) {
        assertThat(listStatus.getText(), containsString(pathologyReview));
      }
  }

  @When("Change the status of Tests")
  public void changeTheStatusOfTests() throws InterruptedException {
    wait.waitForElementToBeVisible(ordersPage.createOption);
    ordersPage.createOption.click();
    Thread.sleep(2000);
    veterinariansPage.openTestId(CreateNewTests.Sample_id.toString());
    Thread.sleep(2000);
    wait.waitForElementToBeVisible(ordersPage.patientName);
    softAssert.assertEquals(
        ordersPage.patientName.getText(), containsString(CreateNewTests.patientName.toString()));
    softAssert.assertEquals(
        ordersPage.vetName.getText(),
        containsString(CreateNewTests.veterinarianFirstName.toString()));
    softAssert.assertEquals(ordersPage.vetEmail.getText(), containsString("moicher@yopmail.com"));
    ordersPage.inputOpenCase.sendKeys("Selenium automation");
    Thread.sleep(2000);
    new Actions(driver).sendKeys(Keys.ENTER).build().perform();
    ordersPage.senReviewButton.click();
    ordersPage.closePopUp.click();
    driver.navigate().refresh();
    wait.waitForElementToBeVisible(testsPage.badges.get(0));
    softAssert.assertEquals(testsPage.badges.get(0).getText(), containsString("In review"));
  }

  @When("Open the latest notification tests")
  public void openTheLatestNotificationTests() {
    wait.waitForElementToBeVisible(homePage.notificationsButton);
    homePage.notificationsButton.click();
    veterinariansPage.openTestId(CreateNewTests.Sample_id.toString());
  }

  @When("Write the response newly added test sample and send it back  to admin")
  public void writeTheResponseNewlyAddedTestSampleAndSendItBackToAdmin()
      throws InterruptedException {
    wait.waitForElementToBeVisible(ordersPage.ordersOption);
    ordersPage.ordersOption.click();
    veterinariansPage.openTestId(CreateNewTests.Sample_id.toString());
    wait.waitForElementToBeVisible(ordersPage.viewResult);
    js.clickElement(ordersPage.viewResult);
    wait.waitForElementToBeVisible(ordersPage.uploadImageButton);
    ordersPage.uploadImageButton.click();
    Thread.sleep(2000);
    ordersPage.attachedFile.sendKeys(filePath + "dog.jpg");
    Thread.sleep(5000);
    if (ordersPage.uploadButton.isEnabled()) {
      js.clickElement(ordersPage.uploadButton);
    }
    Thread.sleep(1000);
    softAssert.assertEquals(
        veterinariansPage.toastMessage.getText(),
        containsString("Images were successfully uploaded."));
    js.scrollDown();
    Thread.sleep(4000);
    ordersPage.textMessageArea.sendKeys(homePage.currentDate());
    js.clickElement(ordersPage.selectCheckBox);
    Thread.sleep(500);
    js.clickElement(ordersPage.sendButtonPopUp);
    Thread.sleep(500);
    js.scrollToElement(ordersPage.completeReviewButton);
    Thread.sleep(2000);
    wait.waitForElementToBeVisible(ordersPage.completeReviewButton);
    js.clickElement(ordersPage.completeReviewButton);
    wait.waitForElementToBeVisible(veterinariansPage.toastMessage);
    softAssert.assertEquals(
        veterinariansPage.toastMessage.getText(), containsString("Test's status updated."));
  }

  @And("Open the latest test order")
  public void openTheLatestTestOrder() {
    veterinariansPage.openTestId(CreateNewTests.Sample_id.toString());
  }

  @When("click on eGFR and double click on image to generate the PV objects")
  public void clickOnEGFRAndDoubleClickOnImageToGenerateThePVObjects() throws InterruptedException {
    wait.waitForElementToBeVisible(ordersPage.ordersOption);
    js.clickElement(ordersPage.ordersOption);
    wait.waitForElementToBeVisible(ordersPage.orderPageTitleText);
    veterinariansPage.openTestId(CreateNewTests.Sample_id.toString());
    wait.waitForElementToBeVisible(ordersPage.viewResult);
    js.clickElement(ordersPage.viewResult);
    wait.waitForElementToBeClickable(ordersPage.uploadImageButton);
    js.clickElement(ordersPage.uploadImageButton);
    ordersPage.attachedFile.sendKeys(filePath + "dog2.jpg");
    ordersPage.attachedFile.sendKeys(filePath + "dog3.jpg");
    wait.waitForElementToBeClickable(ordersPage.uploadButton);
    if (ordersPage.uploadButton.isEnabled()) {
      js.clickElement(ordersPage.uploadButton);
    }
    wait.waitForElementToBeVisible(veterinariansPage.toastMessage);
    softAssert.assertEquals(
        veterinariansPage.toastMessage.getText(),
        containsString("Images were successfully uploaded."));
    ordersPage.closeButtonPopUpWindow.get(1).click();
    wait.waitForElementToBeVisible(ordersPage.manualReviewButton);
    ordersPage.manualReviewButton.click();
    ordersPage.getManualResultVale();
    wait.waitForElementToBeVisible(ordersPage.eGFRCheckBoxButton);
    ordersPage.eGFRCheckBoxButton.click();
    new Actions(driver).moveToElement(ordersPage.imageFrame).build().perform();
    for (int i = 0; i < 3; i++) {
      new Actions(driver).click(ordersPage.imageFrame).build().perform();
    }
    for (int i = 0; i < 12; i++) {
      js.clickElement(ordersPage.numberFieldsAdd);
      Thread.sleep(300);
    }
    js.clickElement(ordersPage.finalizePatientButton);
    wait.waitForElementToBeVisible(ordersPage.yesButton);
    js.clickElement(ordersPage.yesButton);
    wait.waitForElementToBeVisible(ordersPage.alertMessageToast);
    softAssert.assertEquals(
        ordersPage.alertMessageToast.getText(),
        containsString("Clinical Values were successfully updated."));
    Thread.sleep(1000);
    ordersPage.switchToPreviousWindow();
  }

  @Then("PV objects images should available for pathology review")
  public void pvObjectsImagesShouldAvailableForPathologyReview() {
    wait.waitForElementToBeVisible(ordersPage.closeButtonPopUpWindow.get(0));
    ordersPage.closeButtonPopUpWindow.get(0).click();
    driver.navigate().refresh();
    wait.waitForElementToBeVisible(ordersPage.ordersOption);
    js.clickElement(ordersPage.ordersOption);
    wait.waitForElementToBeVisible(ordersPage.orderPageTitleText);
    veterinariansPage.openTestId(CreateNewTests.Sample_id.toString());
    wait.waitForElementToBeVisible(ordersPage.manualReviewButton);
    for (WebElement footerButton : ordersPage.listOfButtonOnPopUpWindow) {
      if (footerButton.getText().contains("Pathology Viewer")) {
        footerButton.click();
      }
    }
    wait.waitForElementToBeVisible(ordersPage.pathologyViewerTitle);
  }
}
