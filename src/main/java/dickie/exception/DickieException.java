package dickie.exception;

/**
 * Represents a custom exception used to signal invalid user input or command errors
 */
public class DickieException extends Exception {
    private final String message;

    /**
     * Creates a new DickieException with a specified error message.
     *
     * @param message Description of the error
     */
    public DickieException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Returns the error message associated with this exception.
     *
     * @return Error message string
     */
    public String getMessage() {
        return this.message;
    }
}
