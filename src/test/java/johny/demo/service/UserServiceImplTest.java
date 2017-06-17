package johny.demo.service;

import johny.demo.model.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan("johny.demo.service")
@RestClientTest(UserService.class)
public class UserServiceImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void setUp() throws Exception {
        this.user = new User(null, "John", "admin");
        this.user = this.entityManager.persist(user);
    }

    @Test
    public void testGetUserByIdForValidUserId() throws Exception {
        User john = this.userService.getUserById(user.getId());
        assertThat(john).isEqualTo(user);
    }

    @Test
    public void testGetUserByIdForInvalidUserId() throws Exception {
        this.thrown.expect(UserNotFoundException.class);
        this.userService.getUserById(this.user.getId() + 1);
    }

    @Test
    public void testGetUserByIdRest() throws Exception {
        this.server.expect(requestTo("http://localhost:8080/user/" + this.user.getId()))
                .andRespond(withSuccess("{\"id\": " + this.user.getId() + ", \"username\":\"John\", \"role\":\"admin\"}", MediaType.APPLICATION_JSON));
        User john = this.userService.getUserByIdRest(user.getId());
        assertThat(john).isEqualTo(user);
    }

    @Test
    public void testGetUserByIdRestForInvalidUserId() throws Exception {
        this.server.expect(requestTo("http://localhost:8080/user/2"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
        this.thrown.expect(UserNotFoundException.class);
        this.userService.getUserByIdRest(2L);
    }

}