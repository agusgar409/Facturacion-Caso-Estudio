package errors;

/**
 * @author Agustin"
 */
public class CreationClassException extends RuntimeException{

    public CreationClassException(Object o) {
        super("resource: ".concat(o.toString()).concat(" error creating the class"));
    }

}
