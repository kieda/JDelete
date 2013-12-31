package kjl.jdelete.drivers;

import kjl.jdelete.gui.Frame;
import java.awt.EventQueue;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The main class/the entry point. 
 * @author zkieda
 */
public class Main {
    /**
     * 
     * @param args takes no args
     */
    public static void main(String args[]) {
        //pretty much stolen from netbeans generated code
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                //wow nimbus looks so great
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (UnsupportedLookAndFeelException ex) {
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                //make frame and set visible
                new Frame().setVisible(true);
            }
        });
    }
    
}
