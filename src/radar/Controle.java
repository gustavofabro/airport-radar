package radar;

import com.intellij.ui.table.JBTable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

public class Controle extends JPanel implements ActionListener, ItemListener {
    private final JButton btnChangePanel;

    private final int MOVER = 0;
    private final int ESCALONAR = 1;
    private final int ROTACIONAR = 2;

    private float x;
    private float y;
    private float angleValue;
    private float raioValue;
    private char mode;
    private float vRaio;
    private float vAng;

    private final JLabel cadLabel;
    private FormattedTextField coord1;
    private FormattedTextField coord2;
    private final JLabel xLabel;
    private final JLabel yLabel;

    private final FormattedTextField dir;
    private final FormattedTextField speed;
    private final JLabel dirLabel;
    private final JLabel speedLabel;

    private final FormattedTextField raio;
    private final FormattedTextField angle;
    private final JLabel rLabel;
    private final JLabel aLabel;

    private final JRadioButton modeCart;
    private final JRadioButton modePolar;
    private final ButtonGroup grupo1;

    private final JButton btnCad;
    private final JButton btnClear;

    //Configuraçao
    private JLabel modoLabel;
    private JComboBox alterModo;
    private JLabel value1Label;
    private JLabel value2Label;
    private FormattedTextField cValue1;
    private FormattedTextField cValue2;
    private FormattedTextField cDeg;
    private JLabel degLabel;
    private JButton btnAppConfig;
    private JButton btnFunc1;

    //Informaçoes
    private static JTable tableInfo;
    private static TableAirplanesModel tableAirplanesModel;

    private MaskFormatter numFormatter;
    private MaskFormatter dirFormatter;
    private JSeparator separator;

    private Cartesian c;
    private RadarController rc;
    private Font font;
    NumberFormatter formatter;

    public Controle(Cartesian cartesian, RadarController radarController) {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(400, 100));
        this.c = cartesian;
        this.rc = radarController;

        formatter = new NumberFormatter(NumberFormat.getInstance());
        formatter.setValueClass(Integer.class);

        Font fontB = new Font("Arial", Font.BOLD, 15);

        //Mascaras
        try {
            numFormatter = new MaskFormatter("#########");
            dirFormatter = new MaskFormatter("###");
        } catch (ParseException ex) {
        }

        //Dash ====================================================================================
        btnChangePanel = new JButton(">>");

        JPanel btnsPanel = new JPanel();
        btnsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbcBtnPanel = new GridBagConstraints();
        gbcBtnPanel.gridwidth = GridBagConstraints.REMAINDER;
        gbcBtnPanel.insets = new Insets(0, 200, 0, 0);

        btnsPanel.add(btnChangePanel, gbcBtnPanel);
        //Dash ====================================================================================


        //Cad =====================================================================================
        cadLabel = new JLabel("Cadastro");
        cadLabel.setFont(fontB);
        coord1 = new FormattedTextField();
        xLabel = new JLabel("X:");
        coord2 = new FormattedTextField();
        yLabel = new JLabel("Y:");

        coord1.setModo('A');
        coord2.setModo('A');

        coord1.setPreferredSize(new Dimension(100, 40));
        coord2.setPreferredSize(new Dimension(100, 40));

        rLabel = new JLabel("Raio:");
        raio = new FormattedTextField();
        raio.setModo('P');
        aLabel = new JLabel("Ângulo:");
        angle = new FormattedTextField();
        angle.setModo('P');

        dirLabel = new JLabel("D:");
        dir = new FormattedTextField();
        dir.setModo('P');
        speedLabel = new JLabel("V:");
        speed = new FormattedTextField();
        speed.setModo('P');

        modeCart = new JRadioButton("Cartesiana");
        modePolar = new JRadioButton("Polar");

        grupo1 = new ButtonGroup();
        grupo1.add(modeCart);
        grupo1.add(modePolar);

        modeCart.setSelected(true);

        btnCad = new JButton("Adicionar");
        btnClear = new JButton("Limpar tudo");

        JPanel cadPanel = new JPanel();
        cadPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbcCad = new GridBagConstraints();

        gbcCad.anchor = GridBagConstraints.FIRST_LINE_START;
        gbcCad.gridx = GridBagConstraints.RELATIVE;
        gbcCad.gridy = GridBagConstraints.RELATIVE;

        //Cadastro
        gbcCad.gridwidth = GridBagConstraints.REMAINDER;
        gbcCad.insets = new Insets(20, 0, 10, 250);
        cadPanel.add(cadLabel, gbcCad);
        gbcCad.insets = new Insets(0, 0, 10, 20);
        cadPanel.add(modeCart, gbcCad);
        gbcCad.gridx = 0;
        gbcCad.gridy = 1;
        gbcCad.insets = new Insets(0, 120, 10, 0);
        cadPanel.add(modePolar, gbcCad);
        gbcCad.insets = new Insets(0, 0, 10, 0);

        //Posiçoes
        gbcCad.anchor = GridBagConstraints.WEST;
        gbcCad.gridx = 0;
        gbcCad.gridy = 2;
        cadPanel.add(xLabel, gbcCad);
        gbcCad.ipadx = 110;
        gbcCad.gridwidth = GridBagConstraints.REMAINDER;
        gbcCad.insets = new Insets(0, 65, 10, 0);
        cadPanel.add(coord1, gbcCad);
        gbcCad.ipadx = 0;
        gbcCad.gridwidth = 1;

        gbcCad.insets = new Insets(0, 0, 10, 0);
        gbcCad.gridx = 0;
        gbcCad.gridy = 3;
        gbcCad.ipady = 10;
        cadPanel.add(yLabel, gbcCad);
        gbcCad.ipady = 0;
        gbcCad.ipadx = 110;
        gbcCad.gridwidth = GridBagConstraints.REMAINDER;
        gbcCad.insets = new Insets(0, 65, 10, 0);
        cadPanel.add(coord2, gbcCad);
        gbcCad.ipadx = 0;
        gbcCad.gridwidth = 1;

        //direçao
        gbcCad.insets = new Insets(0, 200, 10, 0);
        gbcCad.gridx = 0;
        gbcCad.gridy = 2;
        cadPanel.add(dirLabel, gbcCad);
        gbcCad.ipadx = 35;
        gbcCad.gridwidth = GridBagConstraints.REMAINDER;
        gbcCad.insets = new Insets(0, 230, 10, 0);
        cadPanel.add(dir, gbcCad);
        gbcCad.ipadx = 0;
        gbcCad.gridwidth = 1;

        //velocidade
        gbcCad.insets = new Insets(0, 200, 10, 0);
        gbcCad.gridx = 0;
        gbcCad.gridy = 3;
        cadPanel.add(speedLabel, gbcCad);
        gbcCad.ipadx = 35;
        gbcCad.gridwidth = GridBagConstraints.REMAINDER;
        gbcCad.insets = new Insets(0, 230, 10, 0);
        cadPanel.add(speed, gbcCad);
        gbcCad.ipadx = 0;
        gbcCad.gridwidth = 1;

        //Buttons cad e del
        gbcCad.insets = new Insets(0, 0, 0, 0);
        gbcCad.gridx = 0;
        gbcCad.gridy = 4;
        cadPanel.add(btnCad, gbcCad);
        gbcCad.gridwidth = GridBagConstraints.REMAINDER;
        gbcCad.insets = new Insets(0, 90, 0, 0);
        cadPanel.add(btnClear, gbcCad);
        //Cad ===================================================================================

        //Config ================================================================================
        String modoCBox[] = {"Transladar", "Escalonar", "Rotacionar"};
        modoLabel = new JLabel(("Modo"));
        alterModo = new JComboBox(modoCBox);
        value1Label = new JLabel("X:");
        cValue1 = new FormattedTextField();
        value2Label = new JLabel("Y:");
        cValue2 = new FormattedTextField();
        degLabel = new JLabel("Ângulo:");
        cDeg = new FormattedTextField();
        btnAppConfig = new JButton("Aplicar");
        degLabel.setEnabled(false);
        cDeg.setEnabled(false);

        cValue1.setModo('A');
        cValue2.setModo('A');
        cDeg.setModo('A');

        tableInfo = new JTable();
        tableAirplanesModel = new TableAirplanesModel(c);

        tableInfo.setModel(tableAirplanesModel);
        tableInfo.setPreferredScrollableViewportSize(new Dimension(200, 650));
        tableInfo.setFillsViewportHeight(true);
        tableInfo.getColumnModel().getColumn(0).setPreferredWidth(30);
        tableInfo.getColumnModel().getColumn(1).setPreferredWidth(30);

        JPanel configPanel = new JPanel();
        configPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbcConfig = new GridBagConstraints();

        gbcConfig.anchor = GridBagConstraints.FIRST_LINE_START;
        gbcConfig.gridx = GridBagConstraints.RELATIVE;
        gbcConfig.gridy = GridBagConstraints.RELATIVE;

        //Combox Modo
        gbcConfig.insets = new Insets(0, 0, 20, 0);
        gbcConfig.anchor = GridBagConstraints.WEST;
        gbcConfig.gridx = 0;
        gbcConfig.gridy = 1;
        configPanel.add(modoLabel, gbcConfig);
        gbcConfig.ipadx = 110;
        gbcConfig.gridwidth = GridBagConstraints.REMAINDER;
        gbcConfig.insets = new Insets(0, 65, 20, 0);
        configPanel.add(alterModo, gbcConfig);
        gbcConfig.ipadx = 0;
        gbcConfig.gridwidth = 1;
        gbcConfig.insets = new Insets(0, 0, 10, 0);

        //Alter 1
        gbcConfig.anchor = GridBagConstraints.WEST;
        gbcConfig.gridx = 0;
        gbcConfig.gridy = 2;
        configPanel.add(value1Label, gbcConfig);
        gbcConfig.ipadx = 110;
        gbcConfig.gridwidth = GridBagConstraints.REMAINDER;
        gbcConfig.insets = new Insets(0, 65, 10, 0);
        configPanel.add(cValue1, gbcConfig);
        gbcConfig.ipadx = 0;
        gbcConfig.gridwidth = 1;

        //Alter 2
        gbcConfig.insets = new Insets(0, 0, 10, 0);
        gbcConfig.gridx = 0;
        gbcConfig.gridy = 3;
        configPanel.add(value2Label, gbcConfig);
        gbcConfig.ipadx = 110;
        gbcConfig.gridwidth = GridBagConstraints.REMAINDER;
        gbcConfig.insets = new Insets(0, 65, 10, 0);
        configPanel.add(cValue2, gbcConfig);
        gbcConfig.ipadx = 0;
        gbcConfig.gridwidth = 1;
        gbcConfig.insets = new Insets(0, 0, 20, 0);

        //Angulo
        gbcConfig.insets = new Insets(0, 0, 10, 0);
        gbcConfig.gridx = 0;
        gbcConfig.gridy = 4;
        gbcConfig.ipady = 10;
        configPanel.add(degLabel, gbcConfig);
        gbcConfig.ipadx = 110;
        gbcConfig.ipady = 0;
        gbcConfig.gridwidth = GridBagConstraints.REMAINDER;
        gbcConfig.insets = new Insets(0, 65, 10, 0);
        configPanel.add(cDeg, gbcConfig);

        //Btn aplicar
        gbcConfig.ipadx = 0;
        gbcConfig.gridwidth = 1;
        gbcConfig.insets = new Insets(0, 225, 10, 0);
        configPanel.add(btnAppConfig, gbcConfig);

        //Tabela
        gbcConfig.gridy = 5;
        gbcConfig.ipadx = 340;
        gbcConfig.ipady = 140;
        gbcConfig.insets = new Insets(10, 0, 0, 0);
        configPanel.add(new JScrollPane(tableInfo), gbcConfig);
        //Config ================================================================================


        //Panels ================================================================================
        separator = new JSeparator();

        GridBagConstraints gbcPanels = new GridBagConstraints();

        gbcPanels.insets = new Insets(0, 150, 0, 0);
        gbcPanels.gridwidth = GridBagConstraints.REMAINDER;
        add(btnsPanel, gbcPanels);

        gbcPanels.ipadx = 0;

        gbcPanels.insets = new Insets(0, 0, 10, 30);
        gbcPanels.gridwidth = GridBagConstraints.REMAINDER;
        add(cadPanel, gbcPanels);
        gbcPanels.ipadx = 350;
        gbcPanels.ipady = 0;
        gbcPanels.insets = new Insets(0, 0, 30, 0);
        add(separator, gbcPanels);
        gbcPanels.insets = new Insets(0, 0, 0, 300);//190
        add(configPanel, gbcPanels);
        //Panels ================================================================================

        btnChangePanel.addActionListener(this);
        btnChangePanel.setActionCommand("changePanel");
        alterModo.addItemListener(this);
        btnAppConfig.addActionListener(this);
        btnAppConfig.setActionCommand("setConfig");
        btnCad.addActionListener(this);
        btnCad.setActionCommand("addPlane");
        btnClear.addActionListener(this);
        btnClear.setActionCommand("clearAll");
        modeCart.addActionListener(this);
        modeCart.setActionCommand("radioC");
        modePolar.addActionListener(this);
        modePolar.setActionCommand("radioP");

        setMode('c');
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "addPlane":
                if (coord1.getText().trim().equals("")
                        || coord2.getText().trim().equals("")
                        || dir.getText().equals("")
                        || speed.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Valores inválidos!");
                } else {
                    switch (grupo1.getSelection().getActionCommand()) {
                        case "radioC":
                            mode = 'c';
                            break;
                        case "radioP":
                            mode = 'p';
                    }

                    setPlanePos(Float.parseFloat(coord1.getText().trim().trim()),
                            Float.parseFloat(coord2.getText().trim().trim()),
                            Float.parseFloat(dir.getText().trim()),
                            Float.parseFloat(speed.getText().trim()), mode);
                }

                break;
            case "clearAll":
                c.clearAll();
                break;
            case "radioC":
                setMode('c');
                break;
            case "radioP":
                setMode('p');
                break;
            case "setConfig":
                if (cValue1.getText().trim().equals("")
                        || cValue2.getText().trim().equals("")
                        || (cDeg.isEnabled() && cDeg.getText().trim().equals(""))) {
                    JOptionPane.showMessageDialog(null, "Valores inválidos!");
                } else
                    setConfig();
                break;
            case "changePanel":
                rc.setFunctionsPanel();
        }
    }

    private void setMode(char mode) {
        if (mode == 'c') {
            xLabel.setText("X:");
            yLabel.setText("Y:");
            coord1.setModo('A');
        } else {
            xLabel.setText("Raio:");
            yLabel.setText("Ângulo:");
            coord1.setModo('P');
        }

        coord1.setText("");
        coord2.setText("");
    }

    public void setPlanePos(float v1, float v2, float dir, float speed, char mode) {
        if (mode == 'c') {
            x = v1;
            y = v2;

            vRaio = (float) Math.hypot(x, y);
            vAng = (float) Math.toDegrees(Math.atan(y / x));

        } else {           //Polar
            x = (float) (v1 * Math.cos(Math.toRadians(v2)));
            y = (float) (v1 * Math.sin(Math.toRadians(v2)));

            vRaio = v1;
            vAng = v2;
        }

        c.addPlane(x, y, dir, speed, vRaio, vAng);
    }

    public void setConfig() {
        switch (alterModo.getSelectedIndex()) {
            case MOVER:
                c.setTranslate(Float.parseFloat(cValue1.getText().trim()),
                        Float.parseFloat(cValue2.getText().trim()));
                break;
            case ESCALONAR:
                c.setNewScale(Float.parseFloat(cValue1.getText().trim()),
                        Float.parseFloat(cValue2.getText().trim()));
                break;
            case ROTACIONAR:
                c.setNewRotation(Float.parseFloat(cValue1.getText().trim()),
                        Float.parseFloat(cValue2.getText().trim()),
                        Float.parseFloat(cDeg.getText().trim()));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getStateChange() == ItemEvent.SELECTED) {
            cValue1.setText("");
            cValue2.setText("");
            cDeg.setText("");

            switch ("" + e.getItem()) {
                case "Transladar":
                    value1Label.setText("X:");
                    value2Label.setText("Y:");
                    degLabel.setEnabled(false);
                    cDeg.setEnabled(false);
                    break;
                case "Escalonar":
                    value1Label.setText("X (%):");
                    value2Label.setText("Y (%):");
                    degLabel.setEnabled(false);
                    cDeg.setEnabled(false);
                    break;
                case "Rotacionar":
                    value1Label.setText("X:");
                    value2Label.setText("Y:");
                    degLabel.setEnabled(true);
                    cDeg.setEnabled(true);
            }
        }
    }

    public static void updateTable(ArrayList<Airplane> airplane) {
        tableAirplanesModel.clearTable();

        for (int i = 0; i < airplane.size(); i++) {
            tableAirplanesModel.addRow(airplane.get(i));
        }

    }
}