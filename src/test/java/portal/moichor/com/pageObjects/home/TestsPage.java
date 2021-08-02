package portal.moichor.com.pageObjects.home;

import lombok.var;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import portal.moichor.com.base.BasePage;
import java.util.List;

@Component
@Scope("cucumber-glue")
public class TestsPage extends BasePage {

  @FindBy(css = "a[data-flag='tests']")
  public WebElement testsOption;

  @FindBy(css = "div[class*='modal-body'] div[class*='react-select__placeholder']")
  public WebElement placeholder;

  @FindBy(css = "div[class*='modal-body'] div[class='react-select__input'] input")
  public List<WebElement> sendInputValue;

  @FindBy(css = "div[class*='modal-body'] button[class*='btn btn-secondary']")
  public List<WebElement> serviceMenuButton;

  @FindBy(css = ".modal-dialog.wide-modal h5[class*='modal-title']")
  public WebElement titleOfTestAppliesText;

  @FindBy(css = "div[class*='reftest-list-groups-item'] input[type='checkbox']")
  public List<WebElement> checkBoxForTestApplies;

  @FindBy(css = "div[class*='modal-footer'] [class*='btn btn-outline-secondary']")
  public WebElement applyAndCloseButton;

  @FindBy(css = "input[name='id']")
  public WebElement sampleId;

  @FindBy(css = "[type='checkbox'][value='false']")
  public WebElement selectUncheckedBox;

  @FindBy(css = "input[placeholder*='Add your answer here']")
  public WebElement rateStatusText;

  @FindBy(css = "textarea[name='body']")
  public WebElement noteTextBox;

  @FindBy(xpath = "//button[text()='Answer']")
  public WebElement answerButton;

  @FindBy(xpath = "(//input[contains(@id,'react-select')])[7]")
  public WebElement popupVetText;

  @FindBy(css = ".badge.badge-success.badge-pill")
  public List<WebElement> badges;

  @FindBy(css = ".badge.badge-dark.badge-pill")
  public List<WebElement> pathologyReviewStatus;

  @FindBy(css = ".modal-body .badge.badge-info.badge-pill")
  public WebElement testStatusPopupText;

  @FindBy(css = ".modal-body .badge.badge-dark.badge-pill")
  public WebElement pathologyStatusOnPopUpWindow;

  @FindBy(css = ".infinite-scroll-component .mb-2")
  public List<WebElement> testsRowList;

  @FindBy(css = "div[class*='d-flex flex-row'] button[class*='ml-auto']")
  public WebElement historicalTests;

  @FindBy(css = ".modal-content .modal-body  p[class*='overflow-ellipsis']:first-child")
  public List<WebElement> testHistory;

  @FindBy(css = "div[id='orderStatusModal'] textarea[class='form-control']")
  public WebElement addNote;

  @FindBy(css = "div[id='orderStatusModal'] button[class*='ml-1']")
  public WebElement saveNoteButton;

  @FindBy(css = ".infinite-scroll-component .mb-2")
  public List<WebElement> rowList;

  @FindBy(
      xpath =
          "//div[@class='modal-body']//following::button[contains(text(),'Change Billing Status')]")
  public WebElement changeBillingStatusButton;

  @FindBy(id = "is-paid")
  public WebElement selectPaidCheckBox;

  @FindBy(xpath = "//div[@class='modal-body']//following::button[contains(text(),'Update')]")
  public WebElement updateButton;

  @FindBy(css = "div[class*='modal-footer'] button[class*='btn btn-danger']")
  public WebElement yesButton;

  @FindBy(css = "span[class*='badge-pill']")
  public WebElement pendingTest;

  @FindBy(css="button[type='button'][class*='mb-1 btn btn-success btn-xs']")
  public WebElement clinicalCloseButton;

  @FindBy(css = "button[type='button'][name='completed']")
  public WebElement completed;

  @FindBy(css = "button[class*='btn-outline-warning btn-lg']")
  public WebElement requestPathologyReviewButton;

  @FindBy(css = "div[class*='modal-body'] textarea[name='body']")
  public WebElement textareaPathology;

  @FindBy(css = "div[class*='modal-footer'] [class*='btn btn-info']")
  public WebElement submitButton;

  public TestsPage(WebDriver driver) {
    super(driver);
  }

  private static final By Test_ID_Column =
      By.cssSelector(".flex-row .card-body .list-title p:last-child");

  private static final By Go_To_Test =
      By.cssSelector(".btn.btn-outline-secondary.btn-xs.btn-block");

  public void setFeedBackNumber() {
    js.clickElement(rateStatusText);
    noteTextBox.sendKeys("5");
    answerButton.click();
  }

  public void openTestId(String testId) {
    var row = getAlertTestRow(testId);
    click(row.findElement(Test_ID_Column));
  }

  private WebElement getAlertTestRow(String testName) {
    return testsRowList.stream()
        .filter(e -> e.findElement(Test_ID_Column).getText().contains(testName))
        .findAny()
        .orElse(null);
  }

  public void openTesGo(String goToTest) {
    var row = getAlertGoTest(goToTest);
    click(row.findElement(Go_To_Test));
  }

  private WebElement getAlertGoTest(String goTest) {
    return rowList.stream()
        .filter(e -> e.findElement(Go_To_Test).getText().contains(goTest))
        .findAny()
        .orElse(null);
  }

  public String getLatestSampleId() {
    sampleId.click();
    wait.forPage();
    return sampleId.getAttribute("value");
  }
}
