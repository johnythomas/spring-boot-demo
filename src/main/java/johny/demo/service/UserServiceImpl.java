package johny.demo.service;

import johny.demo.model.User;
import johny.demo.repository.UserRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    public UserServiceImpl(UserRepository userRepository, RestTemplateBuilder restTemplateBuilder) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public User getUserById(Long userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        return user;
    }

    @Override
    public User getUserByIdRest(Long userId) throws UserNotFoundException {
        String url = "http://localhost:8080/user/{id}";
        try {
            return this.restTemplate.getForObject(url, User.class, userId);
        } catch (HttpStatusCodeException ex) {
            if (HttpStatus.NOT_FOUND.equals(ex.getStatusCode())) {
                throw new UserNotFoundException(userId, ex);
            }
            throw ex;
        }
    }
}
