package portal.moichor.com.sections;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class Environment {
    public String url;
    public String name;
    private List<String> users;
    @Value("${environment.isActiveDirectoryUser}")
    public boolean isActiveDirectoryUser;
    private static final HashMap<String, User> listOfUsers = new HashMap<>();

    public User getUser(String user) {
        return listOfUsers.get(user);
    }

    public void prepareUsers() {
        users.forEach(users -> {
            List<String> credentials = splitString(users);
            User user = new User(credentials.get(0), credentials.get(1));
            listOfUsers.put(user.username, user);
        });
    }

    private List<String> splitString(String stringToSplit) {
        return Stream.of(stringToSplit.split("/"))
            .map(fragment -> new String(fragment))
            .collect(Collectors.toList());
    }
}
