package errors;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(int code){
        super("The product with the id: " + Integer.toString(code ) + " doesnt exist");
    }
}
