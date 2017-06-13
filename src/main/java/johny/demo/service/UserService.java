package johny.demo.service;

import johny.demo.model.User;

public interface UserService {
    User getUserById(Long userId);

    User getUserByIdRest(Long userId) throws UserNotFoundException;
}
