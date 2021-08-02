package portal.moichor.com.cucumber;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = {"@target/rerun.txt"},
    monochrome = true,
    plugin = {
      "pretty",
      "html:target/cucumber-html-reports",
      "json:target/cucumber-report/cucumber_rerun.json"
    },
    glue = {"portal/moichor/com/stepdefinitions", "portal/moichor/com/base", "portal/moichor/com/cucumber"})
public class RerunFailedTest { // extends AbstractTestNGCucumberTests {
}
