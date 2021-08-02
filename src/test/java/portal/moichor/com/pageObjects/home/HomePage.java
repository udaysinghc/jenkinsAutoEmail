package portal.moichor.com.pageObjects.home;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import portal.moichor.com.base.BasePage;

@Component
@Scope("cucumber-glue")
public class HomePage extends BasePage {
  @FindBy(css = "a[data-flag='veterinarians']")
  public WebElement veterinariansOption;

  @FindBy(css ="a[data-flag='clients']")
  public WebElement clientsOption;

  @FindBy(css="[aria-haspopup='true'] span[class*='name']")
  public WebElement userName;

  @FindBy(css="[class*='dropdown-item']")
  public List<WebElement> userDropDownList;

  @FindBy(id="notificationsButton")
  public WebElement notificationsButton;

  @FindBy(xpath = "(//div[@class='modal-footer']//button[contains(@class,'btn btn-outline-primary')])[2]")
  public WebElement closeTwicePopUpWindow;

  @FindBy(xpath="//button[contains(@class,'dropdown-item') and contains(text(),'Sign out')]")
  public WebElement signOut;


  //Sign out
  public HomePage(WebDriver driver) {
    super(driver);
  }
  public String currentDate() {
    Date date = new Date();
    return new SimpleDateFormat("yyyy-MM-dd HH").format(date);
  }
}
