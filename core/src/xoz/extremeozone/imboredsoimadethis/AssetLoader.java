package xoz.extremeozone.imboredsoimadethis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
    public static Texture background;
    public static Texture textures;
    public static TextureRegion[][] regions;
    public static TextureRegion ship;
    public static TextureRegion bulletImg;
    public static TextureRegion explosion;
    public static TextureRegion alienImg;

    public static void load() {
        background = new Texture(Gdx.files.internal("starback.png"));
        textures = new Texture(Gdx.files.internal("textures.png"));
        regions = TextureRegion.split(textures, 64, 64);
        ship = regions[0][0];
        bulletImg = regions[0][1];
        explosion = regions[1][0];
        alienImg = regions[1][1];
    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        background.dispose();
        textures.dispose();
    }
}
