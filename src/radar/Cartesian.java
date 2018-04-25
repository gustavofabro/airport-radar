package radar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;

import static java.lang.StrictMath.*;

/**
 * Desenha o plano cartesiano e a grade
 */
public class Cartesian extends JPanel implements MouseListener, KeyListener {
    private DecimalFormat df = new DecimalFormat("#0.0##");
    private ArrayList<Airplane> airplanes;
    private ArrayList<Integer> selecteds;

    private String color;
    public boolean drawPlanes = false,
            drawDangerline = false,
            drawColPoint = false,
            hasColision = false;
    private float x,
            y,
            xCol,
            yCol,
            distance,
            distanciaSegura;

    private final int diam = 50;
    private int ids;
    private ArrayList<Integer> indexesSelected;
    private ArrayList<CollisionPoint> collisionPoints;

    private ImageIcon airplaneUnselectedIcon;
    private Image airplaneUnselectedImage;
    private ImageIcon airplaneSelectedIcon;
    private Image airplaneSelectedImage;
    private Image currentImage;
    private Font font;
    private String aviso;
    private JEditorPane avisoPane;

    public Cartesian() {
        setVisible(true);
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        setPreferredSize(new Dimension(800, 600));
        addMouseListener(this);
        this.addKeyListener(this);
        distanciaSegura = 120;
        font = new Font("TimesRoman", Font.LAYOUT_RIGHT_TO_LEFT, 15);
        aviso = "";
        ids = 0;
        avisoPane = new JEditorPane();
        avisoPane.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));

        //Captura da imagem (gambiarra por enquanto)
        airplaneUnselectedIcon = new ImageIcon(
                getClass().getResource("/layout/pStand.png"));
        airplaneUnselectedImage
                = airplaneUnselectedIcon.getImage().getScaledInstance(diam, diam, Image.SCALE_SMOOTH);
        airplaneUnselectedIcon = new ImageIcon(airplaneUnselectedImage);

        airplaneSelectedIcon = new ImageIcon(
                getClass().getResource("/layout/pSelected.png"));
        airplaneSelectedImage
                = airplaneSelectedIcon.getImage().getScaledInstance(diam, diam, Image.SCALE_SMOOTH);
        airplaneSelectedIcon = new ImageIcon(airplaneSelectedImage);

        selecteds = new ArrayList<>();
        airplanes = new ArrayList<>();
        collisionPoints = new ArrayList<>();
        indexesSelected = new ArrayList<>();
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        drawCartesian(g);

        if (drawDangerline) {
            drawFunc2Result(g);
        }

        if (drawColPoint) {
            drawFunc3Result(g);
        }

        if (drawPlanes) {
            drawPlane(g);
        }
    }

    public void drawCartesian(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        //Grade
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.GRAY);

        for (int i = 0; i < getWidth(); i += getWidth() / 10) {
            g2.drawLine(i, 0, i, getHeight());
        }

        for (int i = 0; i < getHeight(); i += getHeight() / 10) {
            g2.drawLine(0, i, getWidth(), i);
        }

        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.GRAY);

        g2.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        g2.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());

        g2.dispose();
    }

    public void drawPlane(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create(); //cria cópia e protege
        AffineTransform old;

        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.CENTER_BASELINE, 13));

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        for (Airplane airp : airplanes) {
            old = g2.getTransform();

            currentImage = !airp.isSelected() ? airplaneUnselectedImage : airplaneSelectedImage;

            //x e y normalizados
            x = (getWidth() / 2 + airp.getX()) - ((currentImage.getWidth(this)) / 2);
            y = (getHeight() / 2 - airp.getY()) - ((currentImage.getHeight(this)) / 2);

            //Tag
            g2.drawString("" + (airp.getId()), x, y);

            //Rotation
            g2.rotate(-Math.toRadians(airp.getDir()), x + (currentImage.getWidth(this) / 2), y
                    + (currentImage.getHeight(this) / 2));

            g2.translate(x, y);

            g2.drawImage(currentImage, 0, 0, this);

            g2.setTransform(old);
        }
        g2.dispose();
    }

    public void drawFunc2Result(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create(); //cria cópia e protege
        AffineTransform old;

        float x1;
        float y1;
        float x2;
        float y2;

        g2.setColor(Color.red);
        g2.setFont(new Font("Arial", Font.CENTER_BASELINE, 13));
        g2.setStroke(new BasicStroke(2));

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        for (int i = 0; i < airplanes.size(); i++) {
            for (int j = i + 1; j < airplanes.size(); j++) {
                distance = (float) Math.hypot(airplanes.get(i).getX() - airplanes.get(j).getX(),
                        airplanes.get(i).getY() - airplanes.get(j).getY());

                x1 = (getWidth() / 2 + airplanes.get(i).getX());
                y1 = (getHeight() / 2 - airplanes.get(i).getY());

                x2 = (getWidth() / 2 + airplanes.get(j).getX());
                y2 = (getHeight() / 2 - airplanes.get(j).getY());

                g2.setColor(distance < distanciaSegura ? Color.RED : Color.BLUE);

                g2.drawLine((int) x1, (int) y1, (int) x2, (int) y2);

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setFont(new Font("Arial", Font.CENTER_BASELINE, 10));
                g2.setColor(new Color(204, 226, 225, 255));

                float dX;
                float dY;

                dX = x1 > x2 ? (x2 + (Math.abs(x1 - x2) / 2)) : (x1 + Math.abs((x1 - x2) / 2));
                dY = y1 > y2 ? (y2 + (Math.abs(y1 - y2) / 2)) : (y1 + Math.abs((y1 - y2) / 2));

                g2.drawString((df.format(distance)) + " km", dX, dY);
            }
        }
        drawDangerline = false;

        g2.dispose();
    }

    public void drawFunc3Result(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create(); //cria cópia e protege
        AffineTransform old;
        int diam = 10;
        float x;
        float y;

        g2.setColor(Color.red);
        g2.setFont(new Font("Arial", Font.CENTER_BASELINE, 13));
        g2.setStroke(new BasicStroke(2));

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

      /*  for (int i = 0; i < airplanes.size(); i++) {
                x = (getWidth() / 2 + airplanes.get(i).getX());
                y = (getHeight() / 2 - airplanes.get(i).getY());

                g2.setColor(hasColision? Color.RED : Color.GREEN);

              //     g2.drawLine((int) x, (int) y, (int) xCol, (int) yCol);

        }
    */
        for (CollisionPoint col : collisionPoints) {
            old = g2.getTransform();

            x = (getWidth() / 2 + col.getX()) - (diam / 2);
            y = (getHeight() / 2 - col.getY()) - (diam / 2);

            g2.translate(x, y);

            g2.fillOval(0, 0, diam, diam);

            g2.setTransform(old);
        }

        drawColPoint = false;

        g2.dispose();
    }

    public void addPlane(float x, float y, float dir, float speed, float vRaio, float vAng) {
        ids++;
        drawPlanes = true;
        airplanes.add(new Airplane(ids, x, y, dir, speed, vRaio, vAng, false)); //false = nao selecioando

        repaint();
    }

    public void setNewScale(float sx, float sy) {
        for (Integer idSelected : selecteds) {
            getAirById(idSelected).setScalePosition(sx / 100, sy / 100);
        }

        repaint();
    }

    public void setTranslate(float x, float y) {
        for (Integer idSelected : selecteds) {
            getAirById(idSelected).setTranslate(x, y);
        }

        repaint();
    }

    public void setNewRotation(float xRotate, float yRotate, float ang) {
        for (Integer idSelected : selecteds) {
            getAirById(idSelected).setRotation(xRotate, yRotate, ang);
        }

        repaint();
    }

    public void calculaDistanciaOrigem(float distanciaAeroporto) {
        if (airplanes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Sem aviões no radar", "Atenção", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        aviso = "<html>";

        for (int i = 0; i < airplanes.size(); i++) {
            color = (airplanes.get(i).getRaio() < distanciaAeroporto) ? "red" : "green";

            aviso += "Avião " + (i + 1) + ": <font color=" + color + "> " + df.format(airplanes.get(i).getRaio()) + " km<br></font>";
        }

        aviso += "</html>";

        showMessageAviso(aviso, "Distância origem");
    }

    public void calculaDistanciaPares(float distanciaSegura) {
        this.distanciaSegura = distanciaSegura;
        drawDangerline = true;

        if (airplanes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Radar vazio", "Atenção", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            if (airplanes.size() == 1) {
                JOptionPane.showMessageDialog(this,
                        "Apenas um avião no radar", "Atenção", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        aviso = "<html>";

        for (int i = 0; i < airplanes.size(); i++) {
            for (int j = i + 1; j < airplanes.size(); j++) {

                distance = (float) Math.hypot(airplanes.get(i).getX() - airplanes.get(j).getX(),
                        airplanes.get(i).getY() - airplanes.get(j).getY());

                color = (distance < distanciaSegura) ? "red" : "green";

                aviso += "<b>Avião " + (i + 1) + " -> " + "Avião " + (j + 1)
                        + ":</b> <font color='" + color + "'>" + df.format(distance) + " km</font><br><br>";
            }
        }
        aviso += "</html>";

        repaint();

        if (!aviso.isEmpty()) {
            showMessageAviso(aviso, "Distâncias");
        }
    }

    public void calculaRotaColisao(float tempoSeguro) {
        float xAux, yAux,
                xNew, yNew,
                d1, d2,
                t1, t2,
                tempoAbsDiferenca = 0,
                tan1, tan2;

        boolean hasColAux1 = false,
                hasColAux2 = false;

        aviso = "<html>";
        collisionPoints.clear();

        for (int i = 0; i < airplanes.size(); i++) {
            for (int j = i + 1; j < airplanes.size(); j++) {
                hasColAux1 = false;
                hasColAux2 = false;
                if ((airplanes.get(i).getDir() == airplanes.get(j).getDir())) {
                    if (!isAnguloIgualComColisao(airplanes.get(i), airplanes.get(j))) {
                        aviso += "<b>Avião " + (i + 1) + " -> " + "Avião " + (j + 1) + "</b><br>"
                                + "<font color=green>Ângulo iguais e sem a existência de um ponto de colisão</font><br><br>";
                        continue;
                    }
                }

                tan1 = (float) tan(toRadians(airplanes.get(i).getDir()));
                tan2 = (float) tan(toRadians(airplanes.get(j).getDir()));

                xAux = (tan1 * -airplanes.get(i).getX()) + airplanes.get(i).getY();
                yAux = (tan2 * -airplanes.get(j).getX()) + airplanes.get(j).getY();

                if (airplanes.get(i).getDir() == 90) {
                    xCol = airplanes.get(i).getX();
                } else {
                    if (airplanes.get(j).getDir() == 90) {
                        xCol = airplanes.get(j).getX();
                    } else {
                        xCol = ((yAux - xAux) / (tan1 + (-tan2)));
                    }
                }

                if (airplanes.get(i).getDir() == 180) {
                    yCol = airplanes.get(i).getY();
                } else {
                    if (airplanes.get(j).getDir() == 180) {
                        yCol = airplanes.get(j).getY();
                    } else {
                        yCol = (tan2 * xCol + yAux);
                    }
                }
                System.out.println("XCol: " + xCol);
                System.out.println("YCol: " + yCol);
                //Validacao
                xNew = airplanes.get(i).getX() + (float) (0.01 * cos(toRadians(airplanes.get(i).getDir())));
                yNew = airplanes.get(i).getY() + (float) (0.01 * sin(toRadians(airplanes.get(i).getDir())));

                System.out.println("1: " + xNew + " " + yNew);

                if (airplanes.get(i).getX() > xCol && xNew < airplanes.get(i).getX()) {
                    hasColAux1 = true;
                } else {
                    if (airplanes.get(i).getX() < xCol && xNew > airplanes.get(i).getX()) {
                        hasColAux1 = true;
                    }
                }

                if (airplanes.get(i).getY() > yCol && yNew < airplanes.get(i).getY()) {
                    hasColAux1 = true;
                } else {
                    if (airplanes.get(i).getY() < yCol && yNew > airplanes.get(i).getY()) {
                        hasColAux1 = true;
                    }
                }


                xNew = airplanes.get(j).getX() + (float) (0.01 * cos(toRadians(airplanes.get(j).getDir())));
                yNew = airplanes.get(j).getY() + (float) (0.01 * sin(toRadians(airplanes.get(j).getDir())));

                System.out.println("2: " + xNew + " " + yNew);

                if (airplanes.get(j).getX() > xCol && xNew < airplanes.get(j).getX()) {
                    hasColAux2 = true;
                } else {
                    if (airplanes.get(j).getX() < xCol && xNew > airplanes.get(j).getX()) {
                        hasColAux2 = true;
                    }
                }

                if (airplanes.get(j).getY() > yCol && yNew < airplanes.get(j).getY()) {
                    hasColAux2 = true;
                } else {
                    if (airplanes.get(j).getY() < yCol && yNew > airplanes.get(j).getY()) {
                        hasColAux2 = true;
                    }
                }

                if (!hasColAux1 || !hasColAux2) {

                    aviso += "<b>Avião " + airplanes.get(i).getId() + " -> " + "Avião " + airplanes.get(j).getId() + "</b><br>"
                            + "<font color=green>Não existe colisão</font><br><br>";
                    continue;
                }

                d1 = (float) Math.hypot(xCol - airplanes.get(i).getX(), (double) yCol - airplanes.get(i).getY());
                d2 = (float) Math.hypot(xCol - airplanes.get(j).getX(), (double) yCol - airplanes.get(j).getY());

                t1 = d1 / airplanes.get(i).getSpeed();
                t2 = d2 / airplanes.get(j).getSpeed();

                tempoAbsDiferenca = (Math.abs(t1 - t2) * 60 * 60);

                color = (tempoAbsDiferenca < tempoSeguro) ? "red" : "green";

                aviso += "<b>Avião " + airplanes.get(i).getId() + " -> " + "Avião " + airplanes.get(j).getId() + "</b><br>"
                        + "Ponto de colisão: (" + df.format(xCol) + ";" + df.format(yCol) + ")<br>"
                        + "Diferença de tempo: <font color='" + color + "'>" + df.format(tempoAbsDiferenca) + "s</font><br>"
                        + "Avião " + airplanes.get(i).getId() + ": " + df.format(t1 * 60 * 60) + "s<br>"
                        + "Avião " + airplanes.get(j).getId() + ": " + df.format(t2 * 60 * 60) + "s<br><br>";

                collisionPoints.add(new CollisionPoint(xCol, yCol));
            }
        }

        aviso += "</html>";

        drawColPoint = true;
        hasColision = tempoAbsDiferenca < tempoSeguro;
        repaint();
        showMessageAviso(aviso, "Colisões");
    }

    public boolean isAnguloIgualComColisao(Airplane a1, Airplane a2) {
        if ((a1.getDir() == 90 || a1.getDir() == 270) && (a1.getX() == a2.getX())) {
            return true;
        } else {
            return (a1.getDir() == 180 || a1.getDir() == 0) && (a1.getY() == a2.getY());
        }
    }

    public boolean validaRadar(Boolean permitOne) {
        if (permitOne) {
            JOptionPane.showMessageDialog(this,
                    "Sem aviões no radar", "Atenção", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return false;
    }

    public void setSelecteds(int rx, int ry) {
        x = (rx - (getWidth() / 2));
        y = ((getHeight() / 2) - ry);

        for (Airplane air : airplanes) {
            if (Math.hypot(x - air.getX(), y - air.getY()) < 5) {
                if (!air.isSelected()) {
                    System.out.println(selecteds.add(air.getId()));
                    air.setState(true);
                } else {
                    selecteds.remove(getIndexOfAir(air.getId()));
                    air.setState(false);
                }
            }
        }
        System.out.println("Size: " + selecteds.size());
    }

    public int getIndexOfAir(int id){
        for (int selected : selecteds){
            if(selected == id){
                return selecteds.indexOf(selected);
            }
        }
        System.out.println("PROBLEM");
        return 0;
    }

    public void clearAll() {
        ids = 0;
        airplanes.clear();
        selecteds.clear();
        repaint();
    }

    public void unselectAll() {
        for (Integer indexSelected : selecteds) {
            getAirById(indexSelected).setState(false); //state = false: unselected
        }
        selecteds.clear();
    }

    public void setSelection(int id, boolean state) {
        if (state) {
            this.selecteds.add(id);
        } else {
            this.selecteds.remove((Integer) id);
        }
    }

    public Airplane getAirById(int id) {

        for (Airplane air : airplanes) {
            if (air.getId() == id) {
                return air;
            }
        }
        return null;
    }

    public void showMessageAviso(String text, String titulo) {
        avisoPane.setText(text);

        JScrollPane scrollPane = new JScrollPane(avisoPane);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        scrollPane.getVerticalScrollBar().setValue(0);
        JOptionPane.showMessageDialog(this,
                scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.setFocusable(true);
        this.requestFocus();
        drawDangerline = false;

        setSelecteds(e.getX(), e.getY());

        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void repaint() {
        super.repaint();

        if (airplanes != null)
            Controle.updateTable(this.getAirplanes());
    }

    public ArrayList<Airplane> getAirplanes() {
        return this.airplanes;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DELETE:
                for (Integer idSelected : selecteds) { //Problema, deve-se retirar do selecteds aqui
                    airplanes.remove(getAirById(idSelected));
                }
                selecteds.clear();
                repaint();

                break;
            case KeyEvent.VK_ESCAPE:
                unselectAll();
                repaint();
                break;
        }
    }
}