package radar;

/**
 * Created by gustavo on 23/06/17.
 */
public class CollisionPoint {
    private float x;
    private float y;

    public CollisionPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
