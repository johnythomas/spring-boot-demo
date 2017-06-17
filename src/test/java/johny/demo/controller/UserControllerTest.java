package johny.demo.controller;

import johny.demo.model.User;
import johny.demo.service.UserNotFoundException;
import johny.demo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User(1L, "John", "admin");
    }

    @Test
    public void testGetUserByIdForValidUserId() throws Exception {
        when(this.userService.getUserById(Mockito.anyLong())).thenReturn(user);
        this.mvc.perform(get("/user/{id}", 1).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 1, 'username':'John', 'role':'admin'}"));
    }

    @Test
    public void testGetUserIdForInvalidUserId() throws Exception {
        doThrow(UserNotFoundException.class).when(this.userService).getUserById(Mockito.anyLong());
        this.mvc.perform(get("/user/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUserUserByIdRestForValidUserId() throws Exception {
        when(this.userService.getUserByIdRest(Mockito.anyLong())).thenReturn(user);
        this.mvc.perform(get("/userRest/{id}", 1).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 1, 'username':'John', 'role':'admin'}"));
    }

    @Test
    public void testGetUserUserByIdRestForInvalidUserId() throws Exception {
        doThrow(UserNotFoundException.class).when(this.userService).getUserByIdRest(Mockito.anyLong());
        this.mvc.perform(get("/userRest/{id}", 1))
                .andExpect(status().isNotFound());
    }

}