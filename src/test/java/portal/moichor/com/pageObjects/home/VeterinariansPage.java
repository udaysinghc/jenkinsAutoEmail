package portal.moichor.com.pageObjects.home;

import java.util.List;
import lombok.var;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import portal.moichor.com.base.BasePage;

@Component
@Scope("cucumber-glue")
public class VeterinariansPage extends BasePage {

  @FindBy(css = "[class*='btn-success']")
  public WebElement addNewButton;

  @FindBy(css = "[class='close']")
  public WebElement closeIconButtonPopupWindow;

  @FindBy(css = "[class='btn btn-secondary']")
  public WebElement cancelButtonPopupWindow;

  @FindBy(css = "[class='modal-title']")
  public WebElement titleOFfVeterinarianPopup;

  @FindBy(name = "fname")
  public WebElement firstNameTextBox;

  @FindBy(name = "lname")
  public WebElement lastNameTextBox;

  @FindBy(name = "email")
  public WebElement emailTextBox;

  @FindBy(css = "[class*='btn-primary']")
  public WebElement createButton;

  @FindBy(css = ".notification-success")
  public WebElement toastMessage;

  @FindBy(css = "label div[invalid='true']")
  public List<WebElement> errorMessageForTextField;

  @FindBy(css = ".infinite-scroll-component .mb-2")
  public List<WebElement> rowList;

  @FindBy(css = ".flex-row .card-body p:first-child")
  public List<WebElement> ClientList;

  @FindBy(css = ".btn-outline-primary")
  public List<WebElement> listOfDeleteButton;

  private static final By RowList_Name = By.cssSelector(".flex-row .card-body p:first-child");
  private static final By Test_ID_Column =
      By.cssSelector(".flex-row .card-body .list-title p:last-child");

  public VeterinariansPage(WebDriver driver) {
    super(driver);
  }

  public void open(String name) {
    var row = getAlertRow(name);
    js.scrollToElement(row.findElement(RowList_Name));
    click(row.findElement(RowList_Name));
  }

  private WebElement getAlertRow(String entityName) {
    return rowList.stream()
        .filter(e -> e.findElement(RowList_Name).getText().contains(entityName))
        .findAny()
        .orElse(null);
  }

  public void openTestId(String testId) {
    var row = getAlertTestRow(testId);
    click(row.findElement(Test_ID_Column));
  }

  private WebElement getAlertTestRow(String testName) {
    return rowList.stream()
        .filter(e -> e.findElement(Test_ID_Column).getText().contains(testName))
        .findAny()
        .orElse(null);
  }

  public String getLatestVetFirstName() {
    firstNameTextBox.click();
    wait.forPage();
    return firstNameTextBox.getAttribute("value");
  }

  public String getLatestVetLastName() {
    lastNameTextBox.click();
    wait.forPage();
    return lastNameTextBox.getAttribute("value");
  }

  public void openLatestDelete() throws InterruptedException {
    for (WebElement listDelete : listOfDeleteButton) {
      listDelete.click();
      Thread.sleep(500);
      break;
    }
  }
}
