package FXManager;
import Manager.Const;
import Manager.Control;
import Utils.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import java.util.HashMap;
public class SoundManager {
    public static HashMap<String, Music> musicHashMap = new HashMap<>();
    public static HashMap<String, Music> soundHashMap = new HashMap<>();

    public static void dispose() {
        for (Music music : musicHashMap.values()) {
            music.dispose();
        }
        musicHashMap.clear();
    }


    public static void playMusic(String musicID, boolean isMusic) {
        if (isMusic) {
            Music music = musicHashMap.get(musicID);
            if (music != null) {
                if (!music.isPlaying() && Control.playMusic()) {
                    music.play();
                }
            }
        } else {
            Music music = soundHashMap.get(musicID);
            if (music != null) {
                if (!music.isPlaying() && Control.playSound()) {
                    music.play();
                }
            }
        }
    }

    public static void setVolume(float f) {
        for (Music music : musicHashMap.values()) {
            music.setVolume(f);
        }
    }

    public static void adjustSoundSettings() {
        for (Music music : musicHashMap.values()) {
            music.setVolume(Control.getVolume());
            if (Control.playMusic()) {
                if (!music.isPlaying()) {
                    music.play();
                }
            } else {
                if (music.isPlaying()) {
                    music.stop();
                }
            }
        }

        for (Music music : soundHashMap.values()) {
            music.setVolume(Control.getVolume());
            if (!Control.playSound()) {
                if (music.isPlaying()) {
                    music.stop();
                }
            }
        }
    }

    public static void parseSoundFXFile() {
        String string = Util.readCryptoFile(Gdx.files.internal("soundFX/SoundDefinitions.txt"), true);
        String[] lines = Util.tokenize(string, '\n');
        String[] params;
        for (String line : lines) {
            if (!line.equals("")) {
                params = Util.tokenize(line, ' ');
                try {
                    Music music = Gdx.audio.newMusic(Gdx.files.internal("soundFX/" + params[0] + ".mp3"));
                    if (Boolean.parseBoolean(params[1])) {
                        music.setLooping(Boolean.parseBoolean(params[2]));
                        musicHashMap.put(params[0], music);
                    } else {
                        soundHashMap.put(params[0], music);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Control.getDeviceManager().sendAnalytic(Const.Analytics_ERROR, e.getLocalizedMessage());
                }
            }
        }
        System.out.println("Loaded Sound Number of Music:" + musicHashMap.size() + " Number of Sounds:" + soundHashMap.size());
        adjustSoundSettings();
    }

}
