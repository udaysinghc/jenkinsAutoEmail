package portal.moichor.com.pageObjects.hashgenerator;

import org.openqa.selenium.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import portal.moichor.com.base.BasePage;
import java.util.Random;

@Component
@Scope("cucumber-glue")
public class MD5HashGenerator extends BasePage {
  public static Random randomGenerator = new Random();
  public static int randomInt = randomGenerator.nextInt(10000);
  public static String hash_id;

  public MD5HashGenerator(WebDriver driver) {
    super(driver);
  }

  public void copyMd5Code() throws InterruptedException {
    ((JavascriptExecutor) driver).executeScript("window.open()");
    switchToTab(1);
    driver.get("https://md5.justyy.workers.dev/?cached&s=test" + randomInt);
    WebElement getlong = driver.findElement(By.cssSelector("pre[style*='word-wrap']"));
    hash_id = getlong.getText().substring(1, getlong.getText().length() - 1);
    switchToTab(0);
    Thread.sleep(2000);
  }

  /* public String last3DigitOfGenerator() {
    String code =hash_id;
    System.out.println("sxdx cfv" + hash_id);
    if (code.length() > 3) {
      System.out.println("tetet" + code.substring(code.length() - 3));
      return code.substring(code.length() - 3);
    } else {
      throw new IllegalArgumentException("word has fewer than 3 characters!");
    }
  }*/
}
