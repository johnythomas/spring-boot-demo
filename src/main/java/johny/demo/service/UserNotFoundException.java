package johny.demo.service;

public class UserNotFoundException extends RuntimeException {

    private Long userId;

    public UserNotFoundException(Long userId) {
        this(userId, null);
    }

    public UserNotFoundException(Long userId, Throwable cause) {
        super("User not found " + userId, cause);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
