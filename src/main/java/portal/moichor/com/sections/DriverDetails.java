package portal.moichor.com.sections;


import java.util.ArrayList;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import portal.moichor.com.enums.BrowserType;
import portal.moichor.com.enums.OperatingSystem;

@Data
public class DriverDetails {
    public boolean isHeadless;
    public boolean shouldHighlightElement;
    @Value("${driver-details.highlightColor}")
    public String highlightColor;
    public BrowserType browserType;
    @Value("${driver-details.os}")
    public OperatingSystem os;
    public boolean isIncognito;
    public String gridUrl;
    public boolean isUsingGrid;
    public ArrayList<String> listeners;
    public ArrayList<String> browserArguments;
    public Boolean isDebugMode;
}
