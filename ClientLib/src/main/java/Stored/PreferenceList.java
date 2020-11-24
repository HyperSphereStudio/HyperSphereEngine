package Stored;
import java.util.ArrayList;

public class PreferenceList {

    public static ArrayList<Preference> preferences = new ArrayList<>();

    public static Preference<Boolean> Music;

    public static Preference<Boolean> SoundFX;

    public static Preference<Boolean> Vibrate;

    public static Preference<Float> Volume;



    public static void setPref(){

        Volume = new Preference<>("Volume", .5f);

        Music = new Preference<>("Music", true);

        SoundFX = new Preference<>("SoundFX", true);

        Vibrate = new Preference<>("Vibrate", true);

    }


    public static void saveSettings(){
        for(Preference preference : PreferenceList.preferences){
            preference.save();
        }
    }



}
