package errors;

/**
 * @author jrodriguez
 */
public class InvalidStatusChanceException extends RuntimeException {
    public InvalidStatusChanceException(Object o) {
        super("cannot be change status ".concat(o.toString()));
    }
}
