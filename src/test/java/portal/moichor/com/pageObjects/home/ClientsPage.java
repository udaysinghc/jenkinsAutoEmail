package portal.moichor.com.pageObjects.home;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import portal.moichor.com.base.BasePage;

@Component
@Scope("cucumber-glue")
public class ClientsPage extends BasePage {
  @FindBy(css = "[class*='btn-success']")
  public WebElement createButton;

  @FindBy(name = "external_id")
  public WebElement externalIdTextBox;

  @FindBy(css = ".modal-footer [class*='btn-danger']")
  public WebElement deleteButton;

  @FindBy(css = "[class*='modal-footer'] [class*='btn-danger']")
  public WebElement deletePopUpButton;

  @FindBy(css = "[role='alert'] div")
  public WebElement alertMessageForLongerName20letters;

  @FindBy(css = "[role='alert']")
  public WebElement alertDeleteToastMessage;

  @FindBy(css = "button[type='button'][class*='dropdown-toggle']")
  public WebElement dropdownButton;

  @FindBy(css = "[class*='dropdown-menu show'] button")
  public List<WebElement> selectClientByName;

  @FindBy(css = "input[placeholder='search']")
  public WebElement searchFilter;

  @FindBy(css = "[class*='false nav-link']")
  public WebElement patientsTab;

  @FindBy(css = "[class*='btn-primary btn-sm']")
  public WebElement saveButton;

  public ClientsPage(WebDriver driver) {
    super(driver);
  }

  public void searchClient(String clientName) {
    dropdownButton.click();
    if (clientName.equalsIgnoreCase("firstName")) {
      selectClientByName.get(0).click();
    } else {
      selectClientByName.get(1).click();
    }
  }
}
