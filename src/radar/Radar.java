package radar;

import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;

/**
 *
 * @author gustavo
 */
public class Radar {

    public static void main(String[] args) {
        RadarController tela = new RadarController();
        tela.setSize(1200, 600);
        tela.setResizable(false);
        tela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tela.setVisible(true);

        JOptionPane.showMessageDialog(null,
                "Trabalho 7º Semestre\n" +
                        "Disciplina: Computação Gráfica\n" +
                        "Professor: Giácomo Antônio Althoff Bolan\n" +
                        "Aluno: Gustavo Fabro", "Sobre", JOptionPane.INFORMATION_MESSAGE);
        }
}
