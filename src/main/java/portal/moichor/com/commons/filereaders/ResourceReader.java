package portal.moichor.com.commons.filereaders;

import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ResourceReader {
    public String getPathToFile(String file) {
        var resource = new ClassPathResource(file);
        var path = "";
        try {
            path =  resource.getFile().getPath();
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return path;
    }
}
