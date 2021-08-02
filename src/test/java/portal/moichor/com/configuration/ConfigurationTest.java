package portal.moichor.com.configuration;

import org.springframework.context.ApplicationContext;
import portal.moichor.com.Config;

// @RunWith(SpringRunner.class)
// @SpringBootTest(classes = Application.class)
public class ConfigurationTest {
  //    @Autowired
  private Config config;
  //    @Autowired
  private ApplicationContext context;
  //    @Test

  public void whenFactoryProvidedThenYamlPropertiesInjected() {
    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxx " + config.driverDetails().os);
  }
}
