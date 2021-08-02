package portal.moichor.com.commons;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;


@Component
public class ScenarioContext {

  private static Map<Object, Object> container;

  public ScenarioContext() {
    container = new HashMap<>();
  }

  public void setToContainer(Object key, Object value) {
    container.put(key.toString(), value);
  }

  public Object getFromContainer(Object key) {
    return container.get(key.toString());
  }
}
