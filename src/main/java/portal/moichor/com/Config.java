package portal.moichor.com;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import portal.moichor.com.sections.DriverDetails;
import portal.moichor.com.sections.Environment;
import portal.moichor.com.sections.Timeout;

@Configuration
// uncomment this if you want to have possibility to select configuration file
// @PropertySource(
//    value = "classpath:config/${config.file}.yml",
//    factory = YamlPropertySourceFactory.class)
@PropertySource(value = "classpath:config/moichor.yml", factory = YamlPropertySourceFactory.class)
@Data
public class Config {

  @Bean
  @ConfigurationProperties(prefix = "driver-details")
  public DriverDetails driverDetails() {
    return new DriverDetails();
  }

  @Bean
  @ConfigurationProperties(prefix = "environment")
  public Environment environment() {
    return new Environment();
  }

  @Bean
  @ConfigurationProperties(prefix = "timeout")
  public Timeout timeout() {
    return new Timeout();
  }
}
