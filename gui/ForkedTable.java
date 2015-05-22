package gui;

import java.util.HashMap;

/**
 * A forked table is like a Python dictionary,
 * except that keys can be lists of items instead
 * of single immutable items.
 * @author Oliver Chu
 */
public class ForkedTable {
    private HashMap<String, Union> mapping = new HashMap<String, Union>();
    
    public ForkedTable() {}
    
    public void bindLongsToRat(long[] lngs, Rational r) {
        String strRepr = "";
        for (Long L : lngs) {
            strRepr += "" + L.hashCode();
        }
        mapping.put(strRepr, new Union(r));
    }
    
    public Rational getRat(long[] lngs) {
        String strRepr = "";
        for (Long L : lngs) {
            strRepr += "" + L.hashCode();
        }
        if (!mapping.containsKey(strRepr)) {
            return null;
        }
        return mapping.get(strRepr).ratVal();
    }
}
