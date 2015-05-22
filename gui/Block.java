package gui;

/**
 *
 * @author Oliver Chu
 */
public class Block {
    private SmartList<SmartList<Pointer>> body;
    private String name;
    
    public Block(String name, SmartList<SmartList<Pointer>> body) {
        this.name = name;
        this.body = body;
    }
    
    public String getName() {
        return name;
    }
    
    public void addToBody(SmartList<Pointer> line) {
        body.add(line);
    }
    
    public Pointer execute(Environment env, SmartList<String> imports,
                           Interpreter interpreter) throws MintException {
        return interpreter.execute(body, imports, env);
    }
}
