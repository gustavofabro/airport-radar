package radar;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gustavo on 04/06/17.
 */
public class Functions extends JPanel implements ActionListener {
    private final JButton btnChangePanel;
    private final JButton btnFunc1;
    private final JButton btnFunc2;
    private final JButton btnFunc3;

    private JLabel func1Label;
    private JLabel func2Label;
    private JLabel func3Label;

    private JLabel distanciaCentroLabel;
    private JLabel distanciaLabel;
    private JLabel tempoLabel;

    private FormattedTextField distanciaAeroporto;
    private FormattedTextField distancia;
    private FormattedTextField tempo;
    JSeparator separator;

    private Cartesian c;
    private RadarController rc;


    public Functions(Cartesian c, RadarController radarController){
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(400, 100));
        separator = new JSeparator();

        Font fontB = new Font("Arial", Font.BOLD, 15);

        //Dash ====================================================================================
        btnChangePanel = new JButton("<<");

        JPanel btnsPanel = new JPanel();
        btnsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbcBtnPanel = new GridBagConstraints();
        gbcBtnPanel.gridwidth = GridBagConstraints.REMAINDER;
        gbcBtnPanel.insets = new Insets(0, 200, 0, 0);

        btnsPanel.add(btnChangePanel, gbcBtnPanel);
        //Dash ====================================================================================

        //Function 1 ==============================================================================
        func1Label = new JLabel("Função 1");
        func1Label.setFont(fontB);
        btnFunc1 = new JButton("Calcular");
        distanciaCentroLabel = new JLabel("Distância aeroporto:");
        distanciaAeroporto = new FormattedTextField();

        JPanel func1Panel = new JPanel();
        func1Panel.setLayout(new GridBagLayout());
        //func2Panel.setBorder(LineBorder.createGrayLineBorder());

        GridBagConstraints gbcFunction1 = new GridBagConstraints();

        gbcFunction1.anchor = GridBagConstraints.FIRST_LINE_START;
        gbcFunction1.gridx = GridBagConstraints.RELATIVE;
        gbcFunction1.gridy = GridBagConstraints.RELATIVE;

        gbcFunction1.gridwidth = GridBagConstraints.REMAINDER;
        func1Panel.add(func1Label, gbcFunction1);
        gbcFunction1.anchor = GridBagConstraints.WEST;
        gbcFunction1.gridx = 0;
        gbcFunction1.gridy = 1;
        gbcFunction1.insets = new Insets(10, 0, 0, 0);
        func1Panel.add(distanciaCentroLabel, gbcFunction1);
        gbcFunction1.ipadx = 110;
        gbcFunction1.gridwidth = GridBagConstraints.REMAINDER;
        gbcFunction1.insets = new Insets(14, 150, 0, 0);
        func1Panel.add(distanciaAeroporto, gbcFunction1);
        gbcFunction1.insets = new Insets(0, 0, 0, 0);
        gbcFunction1.ipadx = 0;
        gbcFunction1.gridwidth = 1;
        gbcFunction1.gridy = 2;
        gbcFunction1.insets = new Insets(15, 0, 0, 0);
        func1Panel.add(btnFunc1, gbcFunction1);
        //Function 1 ==============================================================================

        //Function 2 ==============================================================================
        func2Label = new JLabel("Função 2");
        func2Label.setFont(fontB);
        btnFunc2 = new JButton("Calcular");
        distanciaLabel = new JLabel("Distância segura:");
        distancia = new FormattedTextField();

        JPanel func2Panel = new JPanel();
        func2Panel.setLayout(new GridBagLayout());
        //func2Panel.setBorder(LineBorder.createGrayLineBorder());

        GridBagConstraints gbcFunction2 = new GridBagConstraints();

        gbcFunction2.anchor = GridBagConstraints.FIRST_LINE_START;
        gbcFunction2.gridx = GridBagConstraints.RELATIVE;
        gbcFunction2.gridy = GridBagConstraints.RELATIVE;

        gbcFunction2.gridwidth = GridBagConstraints.REMAINDER;
        func2Panel.add(func2Label, gbcFunction2);
        gbcFunction2.anchor = GridBagConstraints.WEST;
        gbcFunction2.gridx = 0;
        gbcFunction2.gridy = 1;
        gbcFunction2.insets = new Insets(10, 0, 0, 0);
        func2Panel.add(distanciaLabel, gbcFunction2);
        gbcFunction2.ipadx = 110;
        gbcFunction2.gridwidth = GridBagConstraints.REMAINDER;
        gbcFunction2.insets = new Insets(14, 130, 0, 0);
        func2Panel.add(distancia, gbcFunction2);
        gbcFunction2.insets = new Insets(0, 0, 0, 0);
        gbcFunction2.ipadx = 0;
        gbcFunction2.gridwidth = 1;
        gbcFunction2.gridy = 2;
        gbcFunction2.insets = new Insets(15, 0, 0, 0);
        func2Panel.add(btnFunc2, gbcFunction2);
        //Function 2 ==============================================================================

        //Function 3 ==============================================================================
        func3Label = new JLabel("Função 3");
        func3Label.setFont(fontB);
        btnFunc3 = new JButton("Calcular");
        tempoLabel = new JLabel("Tempo:");
        tempo = new FormattedTextField();

        JPanel func3Panel = new JPanel();
        func3Panel.setLayout(new GridBagLayout());
        //func3Panel.setBorder(LineBorder.createGrayLineBorder());

        GridBagConstraints gbcFunction3 = new GridBagConstraints();

        gbcFunction3.anchor = GridBagConstraints.FIRST_LINE_START;
        gbcFunction3.gridx = GridBagConstraints.RELATIVE;
        gbcFunction3.gridy = GridBagConstraints.RELATIVE;

        gbcFunction3.gridwidth = GridBagConstraints.REMAINDER;
        func3Panel.add(func3Label, gbcFunction3);
        gbcFunction3.anchor = GridBagConstraints.WEST;
        gbcFunction3.gridx = 0;
        gbcFunction3.gridy = 1;
        gbcFunction3.insets = new Insets(10, 0, 0, 0);
        func3Panel.add(tempoLabel, gbcFunction3);
        gbcFunction3.ipadx = 110;
        gbcFunction3.gridwidth = GridBagConstraints.REMAINDER;
        gbcFunction3.insets = new Insets(14, 130, 0, 0);
        func3Panel.add(tempo, gbcFunction3);
        gbcFunction3.insets = new Insets(0, 0, 0, 0);
        gbcFunction3.ipadx = 0;
        gbcFunction3.gridwidth = 1;
        gbcFunction3.gridy = 2;
        gbcFunction3.insets = new Insets(15, 0, 0, 0);
        func3Panel.add(btnFunc3, gbcFunction3);
        //Function 3 ==============================================================================

        //Panels ==================================================================================
        GridBagConstraints gbcPanels = new GridBagConstraints();
        gbcPanels.gridwidth = GridBagConstraints.REMAINDER;

        gbcPanels.insets = new Insets(0, 150, 10 , 0);
        add(btnsPanel, gbcPanels);
        gbcPanels.insets = new Insets(0, 0, 0 , 85);
        add(func1Panel, gbcPanels);
        gbcPanels.insets = new Insets(30, 0, 0, 100);
        add(func2Panel, gbcPanels);
        gbcPanels.insets = new Insets(30, 0, 185, 100);
        add(func3Panel, gbcPanels);
        //Panels ===================================================================

        btnChangePanel.addActionListener(this);
        btnChangePanel.setActionCommand("changePanel");
        btnFunc1.addActionListener(this);
        btnFunc1.setActionCommand("func1");
        btnFunc2.addActionListener(this);
        btnFunc2.setActionCommand("func2");
        btnFunc3.addActionListener(this);
        btnFunc3.setActionCommand("func3");

        this.rc = radarController;
        this.c = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "changePanel":
                rc.setControlePanel();
                break;
            case "func1":  //distancia centro
                if(distanciaAeroporto.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(null, "Informe a distância.");
                    return;
                }
                c.calculaDistanciaOrigem(Float.parseFloat(distanciaAeroporto.getText()));
                break;
            case "func2":
                if(distancia.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(null, "Informe a distância.");
                    return;
                }
                c.calculaDistanciaPares(Float.parseFloat(distancia.getText()));
                break;
            case "func3":
                if(tempo.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Informe o tempo.");
                    break;
                }
                c.calculaRotaColisao(Float.parseFloat(tempo.getText().trim()));

        }
    }
}
