package builtin.graphics;

import java.util.ArrayList;

/**
 * A shape is a vector graphic that can be drawn to the screen.
 * @author Oliver Chu
 */
public class Shape {
    public static final int RECTANGLE = 0;
    public static final int OVAL = 1;
    public static final int LINE = 2;
    public static final int ARC = 3;
    public static final int POLYGON = 4;
    public static final int COLOR = 5;
    public static final int NONE = 6;
    private int type;
    private ArrayList<Integer> dimensions;
    
    public Shape(int t, ArrayList<Integer> dim) {
        type = t;
        dimensions = dim;
    }
    
    public int getType() {
        return type;
    }
    
    public ArrayList<Integer> getDimensions() {
        return dimensions;
    }
    
    public void destroy() {
        dimensions = null;
        type = NONE;
    }
}
