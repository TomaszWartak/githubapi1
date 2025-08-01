package pl.dev4lazy.githubapi1.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException( final String message) {
        super(message);
    }
}
