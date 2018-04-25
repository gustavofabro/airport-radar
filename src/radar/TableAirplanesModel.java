package radar;

import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by gustavo on 21/05/17.
 */
public class TableAirplanesModel extends AbstractTableModel {
    private DecimalFormat df = new DecimalFormat("#.0##");
    private ArrayList<Airplane> dados;
    private final String[] colunas = {"", "Id", "X", "Y", "R", "Ang.", "V", "D"};
    Cartesian c;

    public TableAirplanesModel(Cartesian c) {
        dados = new ArrayList<>();
        this.c = c;
    }

    public void addRow(Airplane airplane) {
        this.dados.add(airplane);
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return dados.get(linha).isSelected();
            case 1:
                return dados.get(linha).getId();
            case 2:
                return df.format(dados.get(linha).getX());
            case 3:
                return df.format(dados.get(linha).getY());
            case 4:
                return df.format(dados.get(linha).getRaio());
            case 5:
                return df.format(dados.get(linha).getAng());
            case 6:
                return df.format(dados.get(linha).getSpeed());
            case 7:
                return df.format(dados.get(linha).getDir());
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int linha, int coluna) {
        if (coluna == 0) {
            dados.get(linha).setState((Boolean) aValue);

            c.setSelection(dados.get(linha).getId(), (Boolean) aValue);
            c.repaint();
        }
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        if (columnIndex == 0) return Boolean.class; // assim gera uma Checkbox.
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }

    public void removeRow(int linha) {
        this.dados.remove(linha);
        this.fireTableRowsDeleted(linha, linha); //intervalo

    }

    public void clearTable() {
        this.fireTableRowsDeleted(0, getRowCount());
        this.dados.clear();
    }

    public Airplane getToken(int linha) {
        return this.dados.get(linha);
    }

    @Override //é chamada automaticamente
    public String getColumnName(int num) {
        return this.colunas[num];
    }

    public String[] getColunas() {
        return colunas;
    }
}
