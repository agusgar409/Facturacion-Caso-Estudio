package errors;

/**
 * @author agustin
 */
public class StatusInvalidException extends RuntimeException {

    public StatusInvalidException(Object o) {
        super("resource: ".concat(o.toString()).concat(" status incorrect, pls change it"));
    }

}
