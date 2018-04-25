package radar;

import org.apache.commons.lang.SystemUtils;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class RadarController extends JFrame implements ActionListener {

    //Paineis
    private final Cartesian cartesian;
    private final Controle controle;
    private final Functions functions;

    private final Container c;

    public RadarController() {
        super("Radar");

        c = this.getContentPane();

        try {
            if (System.getProperty("os.name").toLowerCase().indexOf("nux") >= 0){
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            }else {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");           // }
            }
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }
        c.setLayout(new BorderLayout());


        //Plano cartesiano
        cartesian = new Cartesian();

        //Controle
        controle = new Controle(cartesian, this);

        //Function
        functions = new Functions(cartesian, this);

        c.add(BorderLayout.EAST, cartesian);
        c.add(BorderLayout.WEST, controle);

        c.setBackground(Color.DARK_GRAY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void setControlePanel() {
        functions.setVisible(false);
        controle.setVisible(true);
        c.add(BorderLayout.WEST, controle);
    }

    public void setFunctionsPanel() {
        controle.setVisible(false);
        functions.setVisible(true);
        c.add(BorderLayout.WEST, functions);
    }
}