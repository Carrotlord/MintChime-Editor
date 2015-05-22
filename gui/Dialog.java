package gui;

import javax.swing.JOptionPane;

/**
 * This simple class is used to display graphical message boxes.
 * @author Jiangcheng Oliver Chu
 */
public class Dialog {
    public Dialog() {
        super();
    }
    /**
     * Shows a string message in a message box dialog.
     * @param message
     */
    public static void msgBox(String message) {
        JOptionPane.showMessageDialog(null,message,"Info",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows an error message. You can choose whether to exit the program after
     * the error occurs.
     * @param message
     * @param exitProgram
     */
    public static void errorBox(String message,boolean exitProgram) {
        JOptionPane.showMessageDialog(null,message,"Error",
                                      JOptionPane.ERROR_MESSAGE);
        /* if (exitProgram)
            System.exit(0);
         */
    }

    /**
     * Asks the user a Yes/No question. If Yes, returns true.
     * If the user closes the box, the question will be re-asked.
     * @param question
     * @return true if yes, false if no.
     */
    public static boolean askBox(String question) {
        while (true) {
            int choice = JOptionPane.showConfirmDialog(null,question,"Confirm",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION)
                return false;
            if (choice == JOptionPane.YES_OPTION)
                return true;
            //If the user idiotically closes the ConfirmDialog, ask him again!
            errorBox("Please answer the question properly.",false);
        }
    }
}
