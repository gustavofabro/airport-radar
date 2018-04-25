package radar;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class FormattedTextField extends JFormattedTextField {
    private char modo;
    final String regExpString = "[a-zA-Z]";

    public FormattedTextField() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == ',') {
                    e.consume();
                }
                switch (modo) {
                    case 'A':
                        if (Pattern.matches(regExpString, ("" + e.getKeyChar()))
                                || (e.getKeyChar() == '-' && getText().contains("-"))) {
                            e.consume();
                        }
                        break;
                    case 'P':
                        if (Pattern.matches(regExpString, ("" + e.getKeyChar())) || e.getKeyChar() == '-') {
                            e.consume();
                        }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public void setModo(char modo) {
        this.modo = modo;
    }

    public char getModo() {
        return this.modo;
    }

}
