package xoz.extremeozone.imboredsoimadethis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Alien{

    private Vector2 position;
    private int status;
    private int side;
    private Rectangle rectangle;
    private float deadTimer;
    private float sideTimer=0;
    private Random rand = new Random();

    public Alien() {
        position = new Vector2();
        status = 0;
        side = 50;
    }

    public void init(float posX, float posY, float w, float h) {
        position.set(posX,  posY);
        status = 2;
        rectangle = new Rectangle(posX, posY, w, h);
        deadTimer = 0;
    }

    public void update (float delta) {
        // update alien position
        if(sideTimer>2) {
            if(rand.nextInt(2)==0)
                side = -side;
            else
                sideTimer = 0;
        }
        sideTimer += Gdx.graphics.getDeltaTime();
        position.add(side*delta, -1*delta*150);
        rectangle.setPosition(position.x, position.y);

        // if alien is out of screen, set it to dead
        if (position.y < 0) status = 0;
        if (status==1){
            rectangle.set(0,0,0,0);
            if(deadTimer>1)
                status=0;
            else
                deadTimer += delta;
        }
    }

    public Vector2 getPos() {
        return position;
    }
    public int getStatus() {
        return status;
    }
    public Rectangle getRec() { return rectangle; }

    public void setStatus(int status) {
        this.status = status;
    }
}
