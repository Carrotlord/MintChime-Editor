package gui;

import builtin.BuiltinSub;

/**
 *
 * @author Oliver Chu
 */
public class Subprogram {
    private String name;
    private SmartList<Pointer> argNames;
    private SmartList<SmartList<Pointer>> body;
    public SmartList<SmartList<Pointer>> originalBody;
    private BuiltinSub sub;
    private MintObject virtualArgs;
    private int thisPointer;
    private String thisPointerName;
    
    private void copyToOriginalBody() {
        originalBody = new SmartList<SmartList<Pointer>>();
        for (SmartList<Pointer> eachList : body) {
            SmartList<Pointer> newList = new SmartList<Pointer>();
            for (Pointer p : eachList) {
                newList.add(p);
            }
            originalBody.add(newList);
        }
    }
    
    public Subprogram(String name, SmartList<Pointer> argNames, BuiltinSub sub)
    {
        this.name = name;
        this.argNames = argNames;
        this.body = new SmartList<SmartList<Pointer>>();
        this.sub = sub;
        this.virtualArgs = null;
        thisPointer = -1;
        thisPointerName = "";
        copyToOriginalBody();
    }
    
    public Subprogram(String name, SmartList<Pointer> argNames,
                      SmartList<SmartList<Pointer>> body) {
        this.name = name;
        this.argNames = argNames;
        this.body = body;
        this.sub = null;
        this.virtualArgs = null;
        thisPointer = -1;
        thisPointerName = "";
        copyToOriginalBody();
    }

    public Subprogram(String name, SmartList<Pointer> argNames,
                      SmartList<SmartList<Pointer>> body,
                      MintObject virtualArgs) {
        this.name = name;
        this.argNames = argNames;
        this.body = body;
        this.sub = null;
        this.virtualArgs = virtualArgs;
        thisPointer = -1;
        thisPointerName = "";
        copyToOriginalBody();
    }
    
    public void setThisPointer(int value) {
        thisPointer = value;
    }
    
    public void setThisPointerName(String value) {
        thisPointerName = value;
    }
    
    public void setVirtualArgs(MintObject o) {
        virtualArgs = o;
    }
    
    public String getName() {
        return name;
    }
    
    public int getArgNamesLength() {
        return argNames.size();
    }
    
    public SmartList<Pointer> getArgNames() {
        return argNames;
    }
    
    public void addToBody(SmartList<Pointer> line) {
        body.add(line);
        copyToOriginalBody();
    }
    
    public SmartList<SmartList<Pointer>> getBody() {
        return body;
    }
    
    public void setBody(SmartList<SmartList<Pointer>> bod) {
        body = bod;
    }
    
    public Pointer execute(Environment env, SmartList<String> imports,
                           SmartList<Pointer> argValues,
                           Interpreter interpreter) throws MintException {
        MintObject newFrame = new MintObject();
        if (argValues.isEmpty() && argNames.size() == 2) {
            Integer keyword = PointerTools.dereferenceKeyword(argNames.get(1));
            if (keyword != null && keyword == Constants.DOUBLE_DOT) {
                SmartList<Pointer> list = new SmartList<Pointer>();
                String n = PointerTools.dereferenceName(argNames.get(0));
                newFrame.put(n, Heap.allocateList(list));
            }
        }
        int i = 0;
        if (argValues.size() > argNames.size() &&
            !ListTools.containsPointer(argNames,
             new Pointer(Constants.KEYWORD_TYPE, Constants.DOUBLE_DOT))) {
            throw new MintException("Subprogram " + name + " takes " +
                                    argNames.size() + " arguments (" +
                                    argValues.size() + " given)");
        }
        for (Pointer value : argValues) {
            try {
                String n = PointerTools.dereferenceName(argNames.get(i));
                if (i + 1 < argNames.size()) {
                    Integer keyword =
                           PointerTools.dereferenceKeyword(argNames.get(i + 1));
                    if (keyword != null && keyword == Constants.DOUBLE_DOT) {
                        // Store variable args:
                        Pointer list = Heap.allocateList(argValues.subList(i));
                        newFrame.put(n, list);
                        if (PointerTools.dereferenceList(list).get(0).type ==
                            Constants.NULL_TYPE) {
                            newFrame.put(n,
                                   Heap.allocateList(new SmartList<Pointer>()));
                        }
                        break;
                    }
                    i++;
                }
                newFrame.put(n, value);
            } catch (IndexOutOfBoundsException ex) {
                throw new MintException("Subprogram " + name + " takes " +
                                        argNames.size() + " arguments (" +
                                        argValues.size() + " given)");
            }
        }
        if (virtualArgs != null) {
            newFrame.putAll(virtualArgs);
        }
        env.addFrame(newFrame);
        if (thisPointer != -1) {
            interpreter.setThisPointer(thisPointer);
            interpreter.setThisPointerName(thisPointerName);
        } else {
            interpreter.setThisPointer(-1);
            interpreter.setThisPointerName("");
        }
        if (sub != null) {
            env.pop();
            argValues.add(Heap.allocateInterpreter(interpreter));
            return sub.apply(argValues);
        }
        Pointer result = interpreter.execute(body, imports, env);
        body = originalBody;
        copyToOriginalBody();
        return result;
    }
}
