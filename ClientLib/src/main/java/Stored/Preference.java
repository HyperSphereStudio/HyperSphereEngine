package Stored;

import Manager.Control;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Preference<Data> {
    public static Preferences preferences = Gdx.app.getPreferences(Control.getClientSettings().getLibraryName());
    private String string;
    private short c;
    public Data data;
    private static final short i = 0, f = 1, s = 2, b = 3;

    public Preference(String type, Data def){
        this.string = type;
        if(def instanceof Integer){
            c = i;
        }else if(def instanceof Float){
            c = f;
        }else if(def instanceof String){
             c = s;
        }else if(def instanceof Boolean){
            c = b;
        }
        if(!preferences.contains(type)) tempSave(def);
        else data = (Data) getData();
        PreferenceList.preferences.add(this);
    }

    private Object getData(){
        if(c == i){
            return preferences.getInteger(string);
        }else if(c == f){
            return preferences.getFloat(string);
        }else if(c == s){
            return preferences.getString(string);
        }else if(c == b){
            return preferences.getBoolean(string);
        }
        return null;
    }

    public void save(){
        if(c == i){
            preferences.putInteger(string, (Integer) data);
        }else if(c == f){
            preferences.putFloat(string, (Float) data);
        }else if(c == s){
            preferences.putString(string, (String) data);
        }else if(c == b){
            preferences.putBoolean(string, (Boolean) data);
        }
        preferences.flush();
    }

    public void tempSave(Data data){
        this.data = data;
    }

}
