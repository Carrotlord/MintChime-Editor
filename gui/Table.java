package gui;

import java.util.ArrayList;

/**
 * @author Oliver Chu
 */
public class Table extends ArrayList {
    ArrayList<SmartList<Pointer>> contents =
                       new ArrayList<SmartList<Pointer>>();
    
    public void addBinding(SmartList<Pointer> slp) {
        contents.add(slp);
    }
    
    public void bindAll(SmartList<Pointer> wrapper) {
        for (Pointer pt : wrapper) {
            SmartList<Pointer> slp = PointerTools.dereferenceList(pt);
            if (slp.size() == 2) {
                addBinding(slp);
            }
        }
    }
    
    public Pointer hasKey(Pointer key) {
        for (SmartList<Pointer> slp : contents) {
            if (slp.get(0).toString().equals(key.toString())) {
                return Constants.MINT_TRUE;
            }
        }
        return Constants.MINT_FALSE;
    }
    
    @Override
    public SmartList<Pointer> get(int i) {
        return contents.get(i);
    }
    
    public Pointer getValue(Pointer key) {
        for (SmartList<Pointer> slp : contents) {
            if (slp.get(0).toString().equals(key.toString())) {
                return slp.get(1);
            }
        }
        return Constants.MINT_NULL;
    }
    
    @Override
    public String toString() {
        return contents.toString().replace("[", "{").replace("]", "}");
    }
}
