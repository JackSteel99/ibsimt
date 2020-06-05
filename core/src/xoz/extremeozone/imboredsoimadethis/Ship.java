package xoz.extremeozone.imboredsoimadethis;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Ship {

    private Vector3 position;
    private int status;
    private Rectangle rectangle;
    private float timer;

    public Ship(float x, float y, float w, float h) {
        position = new Vector3(x, y, 0);
        status = 2;
        rectangle = new Rectangle(x, y, w, h);
    }

    public void update (float delta) {
        // update Ship position
        rectangle.setPosition(position.x, position.y);

        if (status==1){
            rectangle.set(0,0,0,0);
            if(timer>1)
                status=0;
            else
                timer += delta;
        }
    }

    public Vector3 getPos() {
        return position;
    }
    public int getStatus() {
        return status;
    }
    public Rectangle getRec() { return rectangle; }

    public void setStatus(int status) {
        this.status = status;
    }
    public void setPos(float x, float y){
        position.set(x, y, 0);
    }
}
