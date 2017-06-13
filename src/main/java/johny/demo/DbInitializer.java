package johny.demo;

import johny.demo.model.User;
import johny.demo.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DbInitializer {

    private UserRepository repository;

    public DbInitializer(UserRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        List<User> users = new ArrayList<>();
        users.add(new User(null, "Joe", "admin"));
        users.add(new User(null, "Ron", "contributor"));
        users.add(new User(null, "Bill", "editor"));
        users.add(new User(null, "Smith", "admin"));
        users.add(new User(null, "Charlie", "editor"));
        users.add(new User(null, "Jane", "contributor"));
        repository.save(users);
    }
}
