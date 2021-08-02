package portal.moichor.com.driver;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import portal.moichor.com.Config;
import portal.moichor.com.commons.filereaders.ResourceReader;

@Component
@Log4j2
// todo: implement webdriverservices
public class WebDriverFactory {

  private static final String LINUX_PATH = "driver/linux/chromedriver";
  private static final String WINDOWS_PATH = "driver/windows/chromedriver.exe";
  private static final String MACOS_PATH = "driver/macos/chromedriver";
  private static final String INCOGNITO_BROWSER_ARGUMENT = "--incognito";
  private static final String WINDOW_SIZE_BROWSER_ARGUMENT = "window-size=1200,1100";
  private static final String HEADLESS_BROWSER_ARGUMENT = "--headless";
  private static final String DEBUG_FILE_NAME = "chromedriver_debug.log";
  // these flags are needed to run webdriver in docker
  private static final List<String> MAIN_BROWSER_ARGUMENTS =
      Arrays.asList(
          "--no-sandbox",
          "--disable-dev-shm-usage",
          "--disable-print-preview",
          "disable-gpu",
          "disable-infobars");

  @Autowired private Config config;
  @Autowired private ResourceReader resourceReader;
  @Autowired private ApplicationContext applicationContext;

  // @bean and @scope is registering createDriver as default method for injecting webdriver
  @Bean(destroyMethod = "quit")
  @Scope("cucumber-glue")
  public EventFiringWebDriver createDriver() {
    EventFiringWebDriver driver = null;

    if (config.driverDetails().isUsingGrid) {
      driver = registerListeners(getRemoteChromeDriver());
    } else {
      driver = registerListeners(getChromeDriver());
    }
    setUpWebDriver(driver);

    return driver;
  }

  private ChromeDriver getChromeDriver() {
    return new ChromeDriver(getChromeOptions());
  }

  private String getPathToWebDriver() {
    String pathToDriver;

    switch (config.driverDetails().os) {
      case LINUX:
        pathToDriver = resourceReader.getPathToFile(LINUX_PATH);
        break;
      case MACOS:
        pathToDriver = resourceReader.getPathToFile(MACOS_PATH);
        break;
      case WINDOWS:
      default:
        pathToDriver = resourceReader.getPathToFile(WINDOWS_PATH);
        break;
    }

    return pathToDriver;
  }

  private EventFiringWebDriver registerListeners(WebDriver driver) {
    EventFiringWebDriver wrappedWebDriver = new EventFiringWebDriver(driver);

    for (String driverListener : config.driverDetails().listeners) {
      var listener = (AbstractWebDriverEventListener) applicationContext.getBean(driverListener);
      wrappedWebDriver.register(listener);
    }

    return wrappedWebDriver;
  }

  private RemoteWebDriver getRemoteChromeDriver() {
    RemoteWebDriver driver = null;
    try {
      driver = new RemoteWebDriver(new URL(config.driverDetails().gridUrl), getChromeOptions());
    } catch (MalformedURLException e) {
      log.debug(e.getMessage());
    }
    return driver;
  }

  private ChromeOptions getChromeOptions() {
    var options = new ChromeOptions();
    setHeadlessMode(options);
    setIncognitoMode(options);
    options.addArguments(MAIN_BROWSER_ARGUMENTS);
    setWebdriverBinaryPath();
    setDebugMode();
    return options;
  }

  private void setWebdriverBinaryPath() {
    new File(getPathToWebDriver()).setExecutable(true);
    System.setProperty("webdriver.chrome.driver", getPathToWebDriver());
  }

  private void setDebugMode() {
    if (config.driverDetails().isDebugMode) {
      System.setProperty("webdriver.chrome.logfile", DEBUG_FILE_NAME);
      System.setProperty("webdriver.chrome.verboseLogging", "true");
    }
  }

  private void setHeadlessMode(ChromeOptions options) {
    if (config.driverDetails().isHeadless) {
      options.addArguments(HEADLESS_BROWSER_ARGUMENT);
      options.addArguments(WINDOW_SIZE_BROWSER_ARGUMENT);
    }
  }

  private void setIncognitoMode(ChromeOptions options) {
    if (config.driverDetails().isIncognito) {
      options.addArguments(INCOGNITO_BROWSER_ARGUMENT);
    }
  }

  private void setUpWebDriver(EventFiringWebDriver driver) {
    driver.manage().window().maximize();
    driver.manage().timeouts().pageLoadTimeout(config.timeout().page, TimeUnit.SECONDS);
    driver.manage().timeouts().setScriptTimeout(config.timeout().page, TimeUnit.SECONDS);
  }
}
