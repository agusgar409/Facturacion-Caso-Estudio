package errors;

/**
 * @author jrodriguez
 */
public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(Object o) {
        super(" cannot create an order with this status ".concat(o.toString()));
    }
}
