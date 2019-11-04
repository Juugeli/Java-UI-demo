package muistilistaaja;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                JFrame frame = new MainFrame("Muistilistaaja");
                frame.setResizable(false);
                frame.setSize(550,430);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
