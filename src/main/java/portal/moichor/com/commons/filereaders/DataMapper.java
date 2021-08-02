package portal.moichor.com.commons.filereaders;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import portal.moichor.com.Config;

@Component
public class DataMapper {

  @Autowired private Config config;
  @Autowired private ResourceReader resourceReader;
  private static final String RESOURCES_FOLDER = "testData/";

  public <T> T deserializeYamlFile(Class<T> type, String filePath) throws IOException {
    return deserialize(type, filePath, new YAMLFactory());
  }

  public <T> T deserializeJsonFile(Class<T> type, String filePath) throws IOException {
    return deserialize(type, filePath, new JsonFactory());
  }

  public <T> T getTestData(Class<T> type, String fileName) throws IOException {
    var pathToFile = RESOURCES_FOLDER + config.environment().name + "/" + fileName;
    return deserializeYamlFile(type, pathToFile);
  }

  public <T> List<String> getTestDataFields(Class<T> type) {
    var listOfFields = new ArrayList<String>();
    var mapper = new ObjectMapper();
    var dataType = mapper.getTypeFactory().constructType(type);
    var introspection = mapper.getSerializationConfig().introspect(dataType);
    var properties = introspection.findProperties();
    for (BeanPropertyDefinition property : properties) {
      listOfFields.add(property.getName());
    }
    return listOfFields;
  }

  public Map<String, List<String>> getYamlFileAsMap(String fileName) throws IOException {
    return getFileAsMap(fileName, new YAMLFactory());
  }

  public Map<String, Object> getYamlFileAsStringAndObjectMap(String fileName) throws IOException {
    return getFileAsStringAndListOfObjectMap(fileName, new YAMLFactory());
  }

  public Map<String, List<String>> getJsonFileAsMap(String fileName) throws IOException {
    return getFileAsMap(fileName, new JsonFactory());
  }

  public String getObjectAsString(Object obj) throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(obj);
  }

  private Map<String, List<String>> getFileAsMap(String fileName, JsonFactory factory)
      throws IOException {
    var mapper = new ObjectMapper(factory);
    var pathToFile =
        resourceReader.getPathToFile(RESOURCES_FOLDER + config.environment().name + "/" + fileName);
    return mapper.readValue(new File(pathToFile), Map.class);
  }

  private Map<String, Object> getFileAsStringAndListOfObjectMap(
      String fileName, JsonFactory factory) throws IOException {
    var mapper = new ObjectMapper(factory);
    var pathToFile =
        resourceReader.getPathToFile(RESOURCES_FOLDER + config.environment().name + "/" + fileName);
    return mapper.readValue(new File(pathToFile), Map.class);
  }

  private <T> T deserialize(Class<T> type, String filePath, JsonFactory factory)
      throws IOException {
    var mapper = new ObjectMapper(factory);
    var pathToFile = resourceReader.getPathToFile(filePath);
    mapper.findAndRegisterModules();
    return mapper.readValue(new File(pathToFile), type);
  }
}
