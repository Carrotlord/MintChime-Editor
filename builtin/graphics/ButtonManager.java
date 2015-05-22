package builtin.graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import gui.Interpreter;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;
import gui.Subprogram;

/**
 *
 * @author Oliver Chu
 */
public class ButtonManager implements ActionListener {
    private JButton button;
    private Subprogram sub;
    private SmartList<SmartList<Pointer>> body2;
    private Interpreter i;
    
    ButtonManager(Interpreter interp) {
        i = interp;
        button = new JButton();
        button.setActionCommand("action");
    }
    
    private void copyToBody2(SmartList<SmartList<Pointer>> body) {
        body2 = new SmartList<SmartList<Pointer>>();
        for (SmartList<Pointer> eachList : body) {
            SmartList<Pointer> newList = new SmartList<Pointer>();
            for (Pointer p : eachList) {
                newList.add(p);
            }
            body2.add(newList);
        }
    }
    
    public void addSubprogram(Subprogram s) {
        sub = s;
        // Deep copy the pointer list so that we don't get
        // unwanted modifications.
        copyToBody2(sub.getBody());
        button.addActionListener(this);
    }
    
    public JButton getButton() {
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("action")) {
            try {
                sub.execute(i.getEnv(), i.getImports(),
                            new SmartList<Pointer>(), i);
                sub.setBody(body2);
                copyToBody2(sub.getBody());
            } catch (MintException ex) {
                
            }
        }
    }
}
