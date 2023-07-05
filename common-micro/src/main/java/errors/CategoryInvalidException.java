package errors;

public class CategoryInvalidException extends Exception{
    public CategoryInvalidException(Object o) {
        super("resource: ".concat(o.toString()).concat(" category incorrect, pls change it"));
    }
}
