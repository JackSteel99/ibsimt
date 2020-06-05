package xoz.extremeozone.imboredsoimadethis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {

    private Vector2 position;
    private boolean alive;
    private Rectangle rectangle = new Rectangle();

    /**
     * Bullet constructor. Just initialize variables.
     */
    public Bullet() {
        this.position = new Vector2();
        this.alive = false;
    }

    /**
     * Initialize the bullet. Call this method after getting a bullet from the pool.
     */
    public void init(float posX, float posY, float w, float h) {
        position.set(posX,  posY);
        alive = true;
        rectangle.set(posX, posY, w, h);
    }

    /**
     * Method called each frame, which updates the bullet.
     */
    public void update (float delta) {
        // update bullet position
        position.add(0, 1*delta*80);
        rectangle.setPosition(position.x, position.y);

        // if bullet is out of screen, set it to dead
        if (position.y > Gdx.graphics.getHeight()) alive = false;
    }

    public Vector2 getPos() {
        return position;
    }
    public boolean getAlive() {
        return alive;
    }
    public Rectangle getRec() { return rectangle; }

    public void setAlive(boolean tf) {
        this.alive = tf;
    }
}
