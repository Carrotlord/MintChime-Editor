package gui;

/**
 *
 * @author Oliver Chu
 */
public class Message {
    byte message;
    SmartList<Pointer> contents;
    
    Message(byte message, SmartList<Pointer> contents) {
        this.message = message;
        this.contents = contents;
    }
    
    Message(byte message) {
        this(message, new SmartList<Pointer>());
    }
    
    @Override
    public String toString() {
        return "(" + message + ", " + contents + ")";
    }
}
