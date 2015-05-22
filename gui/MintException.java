package gui;

/**
 *
 * @author Oliver Chu
 */
public class MintException extends Exception {
    private String message;
    
    public MintException(String message) {
        this.message = "Error: " + message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
