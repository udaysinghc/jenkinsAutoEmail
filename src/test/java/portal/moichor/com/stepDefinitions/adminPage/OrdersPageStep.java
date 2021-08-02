package portal.moichor.com.stepDefinitions.adminPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import portal.moichor.com.base.BaseSteps;
import portal.moichor.com.pageObjects.adminPage.OrdersPage;
import portal.moichor.com.pageObjects.home.VeterinariansPage;
import portal.moichor.com.pageObjects.tempEmail.GetNadaTempMail;
import portal.moichor.com.pageObjects.workflowPage.CreateNewTests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@Scope("cucumber-glue")
public class OrdersPageStep extends BaseSteps {
  @Autowired OrdersPage ordersPage;
  @Autowired VeterinariansPage veterinariansPage;
  @Autowired GetNadaTempMail getNadaTempMail;
  public static String filePath =
      System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\moichor\\";

  @Given("navigate to the orders page")
  public void navigateTOTheOrdersPage() {
    wait.waitForElementToBeVisible(ordersPage.ordersOption);
    ordersPage.ordersOption.click();
  }

  @Then("all patient details will be displayed")
  public void allPatientDetailsWillBeDisplayed() {

    wait.waitForElementToBeVisible(ordersPage.patientName);
    assertThat(
        ordersPage.patientName.getText(), containsString(CreateNewTests.patientName.toString()));
    assertThat(
        ordersPage.vetName.getText(),
        containsString(CreateNewTests.veterinarianFirstName.toString()));
    assertThat(ordersPage.vetEmail.getText(), containsString("moicher@yopmail.com"));
  }

  @When("send review by user selected")
  public void sendReviewByUserSelected() throws InterruptedException {
    ordersPage.inputOpenCase.sendKeys("Selenium automation");
    Thread.sleep(2000);
    new Actions(driver).sendKeys(Keys.ENTER).build().perform();
    ordersPage.senReviewButton.click();
    ordersPage.closePopUp.click();
  }

  @And("Also reflected in client side user")
  public void alsoReflectedInClientSideUser() throws InterruptedException {
    getNadaTempMail.loginAsTempUser();
  }

  @And("click on view result button")
  public void clickOnViewResultButton() throws InterruptedException {
    wait.waitForElementToBeVisible(ordersPage.viewResult);
    js.clickElement(ordersPage.viewResult);
    Thread.sleep(2000);
  }

  @When("add images and additional fields like PCV %  eGFR%  , Albumin, Anisocytosis ,AST")
  public void addImagesAndAdditionalFieldsLikePCVEGFRAlbuminAnisocytosisAST()
      throws InterruptedException {
    for (WebElement listOfData : ordersPage.historicalData) {
      listOfData.click();
      Thread.sleep(500);
      for (WebElement enterListOfData : ordersPage.inputHistoricalData) {
        enterListOfData.click();
        Thread.sleep(300);
        enterListOfData.sendKeys("2");
        Thread.sleep(300);
        new Actions(driver).sendKeys(Keys.ENTER).build().perform();
      }
    }
    for (WebElement graphic : ordersPage.graphicsImage) {
      softAssert.assertTrue(graphic.isDisplayed());
    }
    js.clickElement(ordersPage.uploadImageButton);
    ordersPage.attachedFile.sendKeys(filePath + "dog2.jpg");
    wait.waitForElementToBeClickable(ordersPage.uploadButton);
    if (ordersPage.uploadButton.isEnabled()) {
      js.clickElement(ordersPage.uploadButton);
    }
    wait.waitForElementToBeVisible(veterinariansPage.toastMessage);
    softAssert.assertEquals(
        veterinariansPage.toastMessage.getText(),
        containsString("Images were successfully uploaded."));
    js.clickElement(ordersPage.markReadButton);
    Thread.sleep(1000);
  }

  @And("tests status of order is review completed")
  public void testsStatusOfOrderIsReviewCompleted() {
    driver.navigate().refresh();
    wait.waitForElementToBeVisible(ordersPage.ordersOption);
    js.clickElement(ordersPage.ordersOption);
    wait.waitForElementToBeVisible(ordersPage.badgesCompleted);
    softAssert.assertTrue(ordersPage.badgesCompleted.isDisplayed());
  }

  @When("re analyze the latest test sample result")
  public void reAnalyzeTheLatestTestSampleResult() throws InterruptedException {
    wait.waitForElementToBeVisible(ordersPage.viewResult);
    ordersPage.viewResult.click();
    Thread.sleep(2000);
  }

  @Then("successfully  Re-analyze toast message is displayed")
  public void successfullyReAnalyzeToastMessageIsDisplayed() {
    wait.waitForElementToBeVisible(ordersPage.reanalyzeButton);
    js.clickElement(ordersPage.reanalyzeButton);
    wait.waitForElementToBeVisible(ordersPage.inputConfirmText);
    ordersPage.inputConfirmText.sendKeys("confirm");
    ordersPage.confirmDeleteButton.click();
    wait.waitForElementToBeVisible(ordersPage.alertMessageToast);
    softAssert.assertEquals(
        ordersPage.alertMessageToast.getText(),
        containsString("Re-analyze has been done successfully"));
  }

  @When("manual review the latest tests result")
  public void manualReviewTheLatestTestsResult() {
    wait.waitForElementToBeVisible(ordersPage.closeButtonPopUpWindow.get(1));
    ordersPage.closeButtonPopUpWindow.get(1).click();
    wait.waitForElementToBeVisible(ordersPage.manualReviewButton);
    js.clickElement(ordersPage.manualReviewButton);
    ordersPage.getManualResultVale();
    softAssert.assertEquals(ordersPage.inputPvcValue.getAttribute("value"), containsString("2%"));
    softAssert.assertEquals(ordersPage.egfFinalizedValue.getText(), containsString("2%"));
    wait.waitForElementToBeClickable(ordersPage.finalizePatientButton);
    ordersPage.finalizePatientButton.click();
    wait.waitForElementToBeVisible(ordersPage.yesButton);
    ordersPage.yesButton.click();
  }

  @Then("added image  attached and PCV and eGFR  values is displayed")
  public void addedImageAttachedAndPCVAndEGFRValuesIsDisplayed() {
    wait.waitForElementToBeVisible(ordersPage.alertMessageToast);
    softAssert.assertEquals(
        ordersPage.alertMessageToast.getText(),
        containsString("Clinical Values were successfully updated."));
  }
}
