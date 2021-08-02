package portal.moichor.com.pageObjects.adminPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.testng.Assert;
import portal.moichor.com.base.BasePage;

import java.util.List;

@Component
@Scope("cucumber-glue")
public class OrdersPage extends BasePage {
  @FindBy(css = "a[data-flag='orders']")
  public WebElement ordersOption;

  @FindBy(xpath = "//a[contains(text(),'Created Tests')]")
  public WebElement createOption;

  @FindBy(css = "p [class*='font-weight-bold']")
  public WebElement patientName;

  @FindBy(xpath = "//p[contains(text(),'Ordering Veterinarian')]")
  public WebElement vetName;

  @FindBy(xpath = "//p[contains(text(),'Vet') and contains(text(),'Email')]")
  public WebElement vetEmail;

  @FindBy(css = ".modal-footer [class*='btn btn-outline-primary']")
  public WebElement closePopUp;

  @FindBy(css = "[class*='react-select__single-value'] +div input")
  public WebElement inputOpenCase;

  @FindBy(css = ".modal-body .badge.badge-warning.badge-pill")
  public WebElement testStatusBadge;

  @FindBy(xpath = "//button[@type='button' and contains(text(),'View Result')]")
  public WebElement viewResult;

  @FindBy(xpath = "//button[@type='button' and contains(text(),'Upload Image')]")
  public WebElement uploadImageButton;

  @FindBy(css = "input[type='file']")
  public WebElement attachedFile;

  @FindBy(css = ".modal-footer [class*='btn btn-info']")
  public WebElement uploadButton;

  @FindBy(name = "message")
  public WebElement textMessageArea;

  @FindBy(css = "[class='form-check-input']")
  public WebElement selectCheckBox;

  @FindBy(xpath = "//button[@type='button' and contains(text(),'Send')]")
  public WebElement sendButtonPopUp;

  @FindBy(
      xpath =
          "//div[@class='modal-footer']//following::button[@type='button' and contains(text(),'Complete Review')]")
  public WebElement completeReviewButton;

  @FindBy(css = "div[class*='modal-body'] div[class*='w-70'] div")
  public List<WebElement> historicalData;

  @FindBy(css = ".card-body button[class='btn btn-secondary btn-sm']")
  public WebElement markReadButton;

  @FindBy(xpath = "(//span[contains(@class,'badge-pill')])[1]")
  public WebElement badgesCompleted;

  @FindBy(id = "clinicalval")
  public List<WebElement> inputHistoricalData;

  @FindBy(css = "div.test-result-line .test-res-center")
  public List<WebElement> graphicsImage;

  @FindBy(
      xpath =
          "//div[contains(@class,'react-select__single-value')]//preceding::button[contains(@class,'btn btn-orange')]")
  public WebElement senReviewButton;

  @FindBy(xpath = "//div[@class='modal-body']//following::button[contains(@name,'pending')]")
  public WebElement pendingButton;

  @FindBy(css = "[class*='modal-footer'] [class*='btn btn-outline-primary']")
  public List<WebElement> closeButtonPopUpWindow;

  @FindBy(css = "[class*='modal-footer'] [class*='btn btn-purple']")
  public WebElement reanalyzeButton;

  @FindBy(css = "[class*='modal-body'] input[placeholder*='confirm']")
  public WebElement inputConfirmText;

  @FindBy(css = "[class*='modal-footer'] button[class*='btn btn-danger']")
  public WebElement confirmDeleteButton;

  @FindBy(css = "[role='alert'] [class*='message']")
  public WebElement alertMessageToast;

  @FindBy(css = "[class*='modal-footer'] a[class*='btn btn-primary']")
  public WebElement manualReviewButton;

  @FindBy(css = "[class*='modal-footer'] [class*='btn btn-primary']")
  public List<WebElement> listOfButtonOnPopUpWindow;

  @FindBy(css = "img[src*='.jpg']")
  public List<WebElement> loadedImageSource;

  @FindBy(xpath ="(//img[contains(@src,'.jpg')])[1]")
  public WebElement imageFrame;

  @FindBy(css = "input[inputmode='numeric'][name='pcv']")
  public WebElement inputPvcValue;

  @FindBy(css = "div[class*='w-30 d-flex']")
  public WebElement egfFinalizedValue;

  @FindBy(css = "div[class*='d-flex'] button[class*='mt-2 btn btn-success']")
  public WebElement finalizePatientButton;

  @FindBy(css = "div[class*='modal-footer'] button[class*='btn btn-danger']")
  public WebElement yesButton;

  @FindBy(css = "button[class*='label-button']")
  public WebElement eGFRCheckBoxButton;

  @FindBy(css = "div[class*='row'] div[class*='mb-2'] h1")
  public WebElement orderPageTitleText;

  @FindBy(xpath = "//button[@type='button']//span[text()='+']")
  public WebElement numberFieldsAdd;

  @FindBy(css=".pathology-viewer-modal .modal-title")
  public WebElement pathologyViewerTitle;

  public OrdersPage(WebDriver driver) {
    super(driver);
  }

  public void getManualResultVale() {
    switchToTab(1);
    for (WebElement listOfImage : loadedImageSource) {
      Assert.assertTrue(listOfImage.isDisplayed());
    }
  }

  public void switchToPreviousWindow() {
    switchToTab(0);
  }
}
