package radar;

import static java.lang.StrictMath.*;

/**
 * Created by gustavo on 07/05/17.
 */
public class Airplane {
    private int id;
    private double x;
    private double y;
    private boolean selected;
    private float raio;
    private float ang;
    private float dir;
    private float speed;
    private float sx;
    private float sy;
    private float rx;
    private float ry;
    private float rotateAng;

    public Airplane(int id, float x, float y, float dir, float speed, float raio, float ang, boolean selected) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.speed = speed;
        this.raio = raio;
        this.ang = ang;
        this.selected = selected;
        this.sx = 0;
        this.sy = 0;
        this.rx = 0.0f;
        this.ry = 0.0f;
        this.rotateAng = 0;
    }

    public float getX() {
        return (float) x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return (float) y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getAng() {
        return ang;
    }

    public void setAng(float angle) {
        this.ang = angle;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setState(boolean selected) {
        this.selected = selected;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDir() {
        return dir;
    }

    public void setDir(float dir) {
        this.dir = dir;
    }

    public float getSx() {
        return sx;
    }

    public float getSy() {
        return sy;
    }

    public float getRx() {
        return rx;
    }

    public void setRx(float rx) {
        this.rx = rx;
    }

    public float getRy() {
        return ry;
    }

    public void setRy(float ry) {
        this.ry = ry;
    }

    public float getRaio() {
        return raio;
    }

    public void setRadio(float raio) {
        this.raio = raio;
    }

    public float getRotateAng() {
        return this.rotateAng;
    }

    public void setTranslate(float tX, float tY) {
        this.x += tX;
        this.y += tY;

        updateValues();
    }

    public void setScalePosition(float sx, float sy) {
        this.x += (this.x * sx);
        this.y += (this.y * sy);

        updateValues();
    }

    public void setRotation(float xRotate, float yRotate, float ang) {
        float mx, my;

        mx = (float) (x - xRotate);
        my = (float) (y - yRotate);

        this.x = (mx * cos(toRadians(ang)) - (my * sin(toRadians(ang)))) + xRotate;
        this.y = (my * cos(toRadians(ang)) + (mx * sin(toRadians(ang)))) + yRotate;

        updateValues();
    }

    public void updateValues() {
        float angAux;
        angAux = (float) Math.toDegrees(Math.atan(y / x));

        if((x < 0 && y > 0) || (x < 0 && y < 0)) {
            angAux+=180;
        }else {
            if(x > 0 && y < 0){
                angAux+=360;
            }
        }
        this.raio = (float) Math.hypot(x, y);
        this.ang = angAux;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

