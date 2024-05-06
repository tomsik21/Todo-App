package todoapp.exception;

public class TodoNotFoundException extends RuntimeException {
    private static final long serialVerisionUID = 1;

    public TodoNotFoundException(String message) {
        super(message);
    }
}
