package portal.moichor.com.cucumber;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import lombok.var;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import portal.moichor.com.Config;
import portal.moichor.com.logger.ConsolePrinter;
import portal.moichor.com.logger.options.Color;
import portal.moichor.com.logger.options.Emoji;

/***
 * features - path to folder with feature files. You can provide many paths by adding it to
 * array eg. features = {"src/test/java/com/bst/features1", "src/test/java/com/bst/features2"}
 * glue - this path contains all folders related to cucumber in this case it will be:
 * 1. path where cucumber will be searching for steps and BaseSteps.java: com/bst/stepDefinitions"
 * 2. path where cucumber will be looking for hooks: "com/bst/cucumber"
 * plugin - list of plugins executed with cucumber
 */
@CucumberOptions(
    features = "src/test/java/portal/moichor/com/features",
    glue = {"portal/moichor/com/stepDefinitions", "portal/moichor/com/base", "portal/moichor/com/cucumber"},
    monochrome = true,
    plugin = {
      "pretty",
      "html:target/cucumber-html-reports",
      "json:target/cucumber-report/cucumber.json",
      "rerun:target/rerun.txt"
    })
public class CucumberTest extends AbstractTestNGCucumberTests {

  @Autowired public ConsolePrinter printer;

  @Autowired public Config config;

  @Autowired public WebDriver driver;

  @Override
  @DataProvider(parallel = true)
  public Object[][] scenarios() {
    return super.scenarios();
  }

  @BeforeClass
  public static void beforeTestRun() {
    System.out.println(">>>>>>>>>>>>>> Prepare data before test suite run etc.");
  }

  @AfterClass
  public static void afterTestRun() {
    System.out.println(">>>>>>>>>>>>>> Execute any cleanup methods here etc.");
  }

  @Before()
  public void beforeScenario(Scenario scenario) {
    printer
        .emoji(Emoji.ZAP)
        .color(Color.YELLOW)
        .bold()
        .print("[Starting scenario]: " + scenario.getName())
        .reset();
    driver.get(config.environment().url);
  }

  @After()
  public void afterScenario(Scenario scenario) {
    if (scenario.isFailed()) {
      printer
          .color(Color.RED)
          .bold()
          .emoji(Emoji.X)
          .print("[Scenario failed]: " + scenario.getName())
          .reset();
      var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
      var imageFileName =
          new SimpleDateFormat("yyyy-MM-dd_HH-ss").format(new GregorianCalendar().getTime());
      scenario.attach(screenshot, "image/png", imageFileName);
    } else {
      printer
          .color(Color.GREEN)
          .bold()
          .emoji(Emoji.STAR)
          .print("[Scenario passed]: " + scenario.getName())
          .reset();
    }
  }
}
