package builtin.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * A MintWindow is a frame or desktop window designed for running graphical
 * applications such as 3D graphing software or PC/Mac/Linux videogames.
 * @author Oliver Chu
 */
public class MintWindow extends JFrame {
    private ArrayList<Shape> shapes;
    public MintWindow() {
        shapes = new ArrayList<Shape>();
    }
    
    @Override
    public void paint(Graphics g) {
        Image dbImg = createImage(getWidth(), getHeight());
        Graphics dbg = dbImg.getGraphics();
        draw(dbg);
        g.drawImage(dbImg, 0, 0, this);
    }

    private void draw(Graphics g) {
        for (Shape s : shapes) {
            drawShape(g, s);
        }
        repaint();
    }
    
    public void addShape(Shape s) {
        shapes.add(s);
    }
    
    public void clearShapes() {
        shapes = new ArrayList<Shape>();
    }

    private void drawShape(Graphics g, Shape s) {
        ArrayList<Integer> dim = s.getDimensions();
        switch (s.getType()) {
            case Shape.RECTANGLE:
                g.drawRect(dim.get(0), dim.get(1), dim.get(2), dim.get(3));
                break;
            case Shape.OVAL:
                g.drawOval(dim.get(0), dim.get(1), dim.get(2), dim.get(3));
                break;
            case Shape.LINE:
                g.drawLine(dim.get(0), dim.get(1), dim.get(2), dim.get(3));
                break;
            case Shape.ARC:
                g.drawArc(dim.get(0), dim.get(1), dim.get(2), dim.get(3),
                          dim.get(4), dim.get(5));
                break;
            case Shape.POLYGON:
                Polygon p = new Polygon();
                for (int i = 0; i + 1 < dim.size(); i += 2) {
                    p.addPoint(dim.get(i), dim.get(i + 1));
                }
                g.drawPolygon(p);
                break;
            case Shape.COLOR:
                int red = dim.get(0);
                int green = dim.get(1);
                int blue = dim.get(2);
                g.setColor(new Color(red, green, blue));
                // We only need to set the color once.
                //s.destroy();
                break;
            default:
                break;
        }
    }
}
