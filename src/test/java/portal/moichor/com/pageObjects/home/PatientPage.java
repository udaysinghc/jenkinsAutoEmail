package portal.moichor.com.pageObjects.home;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import portal.moichor.com.base.BasePage;

@Component
@Scope("cucumber-glue")
public class PatientPage extends BasePage {

  @FindBy(css = "a[data-flag='patients']")
  public WebElement patientOption;

  @FindBy(name = "name")
  public WebElement patientNameTextBox;

  @FindBy(css = "input[placeholder*='Click to select a date']")
  public WebElement DOBTextBox;

  @FindBy(css = "[id*='react-select']")
  public List<WebElement> dropDownOption;

  @FindBy(css = "input[id*='react-select-3-input']")
  public WebElement clientsSearchTextBox;

  @FindBy(css="input[id*='react-select-2-input']")
  public WebElement selectSex;

  @FindBy(css = "div[class*='react-select__menu-list'] div")
  public List<WebElement> listOfClient;

  @FindBy(css= "input[id*='react-select-4-input']")
  public WebElement speciesSearchTextBox;

  @FindBy(name = "weight")
  public WebElement weightTextBox;

  @FindBy(id = "TooltipExample")
  public WebElement searchFilter;

  @FindBy(css = "[type='button'][class*='btn-danger btn-sm']")
  public WebElement deleteButton;

  @FindBy(css = "[class*='modal-footer'] [class*='btn btn-danger']")
  public WebElement deleteButtonPopUP;

  @FindBy(css="div[class*='popover-body']")
  public List<WebElement> tooltipMessage;

  @FindBy(css="div[role='alert']")
  public WebElement deleteSuccessToastMessage;

  public PatientPage(WebDriver driver) {
    super(driver);
  }

  public void selectSex(String genderType) {
    dropDownOption.get(0).click();
    wait.forPage(30);
    switch (genderType) {
      case "Male":
        driver.findElement(By.xpath("//*[text()='Male']")).click();
        break;
      case "Female":
        driver.findElement(By.xpath("//*[text()='Female']")).click();
        break;
      default:
        throw new IllegalArgumentException("Incorrect gender type");
    }
  }
  public String getLatestPatientName() {
    patientNameTextBox.click();
    wait.forPage();
    return  patientNameTextBox.getAttribute("value");
  }
}
