package xoz.extremeozone.imboredsoimadethis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {

	private static int WORLD_WIDTH;
	private static int WORLD_HEIGHT;

	private OrthographicCamera cam;

	private SpriteBatch batch;

	private float bulletTime = 0;
    // array containing the active bullets.
    private final Array<Bullet> activeBullets = new Array<>();
    private final Array<xoz.extremeozone.imboredsoimadethis.Alien>  activeAliens  = new Array<>();
    private Ship ship;

    private Random rand;
    private float deadTime;

    private BitmapFont font;

	@Override
	public void create() {
		WORLD_WIDTH =  Gdx.graphics.getWidth();
		WORLD_HEIGHT = Gdx.graphics.getHeight();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);

        batch = new SpriteBatch();
        rand = new Random();
        deadTime = 0;

        xoz.extremeozone.imboredsoimadethis.AssetLoader.load();
        xoz.extremeozone.imboredsoimadethis.SoundEffects.load();
        ship = new Ship(500, 0, xoz.extremeozone.imboredsoimadethis.AssetLoader.ship.getRegionWidth()*2, xoz.extremeozone.imboredsoimadethis.AssetLoader.ship.getRegionHeight()*2);

        font = new BitmapFont();
	}

	private void update(float delta) {
		cam.update();
		if(Gdx.input.isTouched()) {
			ship.setPos(Gdx.input.getX(), Gdx.input.getY());
			cam.unproject(ship.getPos());
			bulletTime += delta;
		}
        ship.update(Gdx.graphics.getDeltaTime());
		if(bulletTime >= 0.2) {
			bulletTime -= 0.2;
			// Spawn a new bullet:
			Bullet bullet = new Bullet();
			bullet.init(ship.getPos().x, ship.getPos().y, xoz.extremeozone.imboredsoimadethis.AssetLoader.bulletImg.getRegionWidth(), xoz.extremeozone.imboredsoimadethis.AssetLoader.bulletImg.getRegionHeight());
			activeBullets.add(bullet);
            xoz.extremeozone.imboredsoimadethis.SoundEffects.pew();
			// Spawn a new Alien:
			xoz.extremeozone.imboredsoimadethis.Alien alien = new xoz.extremeozone.imboredsoimadethis.Alien();
			alien.init(rand.nextInt(WORLD_WIDTH-128), WORLD_HEIGHT, xoz.extremeozone.imboredsoimadethis.AssetLoader.alienImg.getRegionWidth()*2, xoz.extremeozone.imboredsoimadethis.AssetLoader.alienImg.getRegionHeight()*2);
			activeAliens.add(alien);
		}
        for(Bullet bullet: activeBullets){
			bullet.update(Gdx.graphics.getDeltaTime());
			// Statements for removing bullets
        	if(!bullet.getAlive()) {
        		activeBullets.removeValue(bullet, true);
			}
		}
		for(xoz.extremeozone.imboredsoimadethis.Alien alien: activeAliens){
			alien.update(Gdx.graphics.getDeltaTime());
			// Statements for removing aliens
			if(alien.getStatus()==0) {
				activeAliens.removeValue(alien, true);
			}
			if(alien.getRec().overlaps(ship.getRec())) {
                ship.setStatus(1);
                xoz.extremeozone.imboredsoimadethis.SoundEffects.boom();
            }
		}
		// Run through both arrays of aliens and bullets
		for(Bullet bullet: activeBullets) {
			for (xoz.extremeozone.imboredsoimadethis.Alien alien : activeAliens) {
				if(alien.getRec().overlaps(bullet.getRec())) {
					alien.setStatus(1);
					bullet.setAlive(false);
					xoz.extremeozone.imboredsoimadethis.SoundEffects.boom();
				}
			}
		}
	}

	@Override
	public void render() {
		if (!(ship.getStatus() == 0)) {
			update(Gdx.graphics.getDeltaTime());
			//Drawing
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			batch.draw(xoz.extremeozone.imboredsoimadethis.AssetLoader.background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
			// Ship
			if (ship.getStatus() == 1)
				batch.draw(xoz.extremeozone.imboredsoimadethis.AssetLoader.explosion, ship.getPos().x - (xoz.extremeozone.imboredsoimadethis.AssetLoader.ship.getRegionWidth()), ship.getPos().y,
						xoz.extremeozone.imboredsoimadethis.AssetLoader.explosion.getRegionWidth() * 2, xoz.extremeozone.imboredsoimadethis.AssetLoader.explosion.getRegionHeight() * 2);
			else
				batch.draw(xoz.extremeozone.imboredsoimadethis.AssetLoader.ship, ship.getPos().x - (xoz.extremeozone.imboredsoimadethis.AssetLoader.ship.getRegionWidth()), ship.getPos().y,
						xoz.extremeozone.imboredsoimadethis.AssetLoader.ship.getRegionWidth() * 2, xoz.extremeozone.imboredsoimadethis.AssetLoader.ship.getRegionHeight() * 2);
			// Bullet
			for (Bullet bullet : activeBullets) {
				batch.draw(xoz.extremeozone.imboredsoimadethis.AssetLoader.bulletImg, bullet.getPos().x, bullet.getPos().y);
			}
			// Alien
			for (Alien alien : activeAliens) {
				if (alien.getStatus() == 2) // Exploded
					batch.draw(xoz.extremeozone.imboredsoimadethis.AssetLoader.alienImg, alien.getPos().x, alien.getPos().y,
							xoz.extremeozone.imboredsoimadethis.AssetLoader.alienImg.getRegionWidth() * 2, xoz.extremeozone.imboredsoimadethis.AssetLoader.alienImg.getRegionHeight() * 2);
				if (alien.getStatus() == 1) // Dead
					batch.draw(xoz.extremeozone.imboredsoimadethis.AssetLoader.explosion, alien.getPos().x, alien.getPos().y,
							xoz.extremeozone.imboredsoimadethis.AssetLoader.explosion.getRegionWidth() * 2, xoz.extremeozone.imboredsoimadethis.AssetLoader.explosion.getRegionHeight() * 2);
			}
			batch.end();
		} else if (deadTime < 4) {
		    if(deadTime==0)
		        xoz.extremeozone.imboredsoimadethis.SoundEffects.gameOver();
			deadTime += Gdx.graphics.getDeltaTime();
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			font.draw(batch, "GAME OVER", WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
			batch.end();
		} else {
			dispose();
			Gdx.app.exit();
		}
	}

	@Override
	public void dispose() {
		AssetLoader.dispose();
		batch.dispose();
		font.dispose();
        SoundEffects.dispose();
	}
}
