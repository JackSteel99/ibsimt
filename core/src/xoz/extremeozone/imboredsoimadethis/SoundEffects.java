package xoz.extremeozone.imboredsoimadethis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundEffects {
    public static Sound pew;
    public static Sound boom;
    public static Sound gameOver;

    public static void load() {
        pew = Gdx.audio.newSound(Gdx.files.internal("pew.mp3"));
        boom = Gdx.audio.newSound(Gdx.files.internal("boom.mp3"));
        gameOver = Gdx.audio.newSound(Gdx.files.internal("gameOver.mp3"));
    }

    public static void pew() {
        pew.play();
    }
    public static void boom() {
        boom.play();
    }
    public static void gameOver() {
        gameOver.play();
    }

    public static void dispose() {
        pew.dispose();
        boom.dispose();
        gameOver.dispose();
    }
}
