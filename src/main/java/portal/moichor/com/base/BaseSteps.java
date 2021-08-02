package portal.moichor.com.base;



import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Scope;
import org.testng.asserts.SoftAssert;
import portal.moichor.com.Application;
import portal.moichor.com.commons.JsExecutor;
import portal.moichor.com.wait.ExplicitWait;

@CucumberContextConfiguration
@SpringBootTest(classes = Application.class)
@Scope("cucumber-glue")
public abstract class BaseSteps {

  @Autowired public WebDriver driver;
  @Autowired public ExplicitWait wait;
  @Autowired public JsExecutor js;
  public final SoftAssert softAssert = new SoftAssert();

  public Actions actions;

  public Actions getActions() {
    if (actions == null) {
      actions = new Actions(driver);
    }
    return actions;
  }
}
