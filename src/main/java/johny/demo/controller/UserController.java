package johny.demo.controller;

import johny.demo.model.User;
import johny.demo.service.UserNotFoundException;
import johny.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable Long id) {
        return this.userService.getUserById(id);
    }

    @GetMapping(path = "userRest/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserUserByIdRest(@PathVariable Long id) {
        return this.userService.getUserByIdRest(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleVinNotFound(UserNotFoundException ex) {
    }
}