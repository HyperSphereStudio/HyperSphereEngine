package Appl.WebSever;

import Manager.Control;
import Utilities.StringManager;

import java.util.HashMap;

public class URLManager {

    public static String Secret = "&s=", Action = "&a=", Key = "&k=", Type = "&t=", Type_Player = "Player", Type_Server = "Server";

    public HashMap<String, String> map = new HashMap<>();

    public URLManager(){

    }

    public void newLink(String action, String secret){
        map.put(action, secret);
    }

    public void removeLink(String action){
        map.remove(action);
    }

    public static String Urlify(String key, String action, URLManager URLManager, String type){
        String secret = StringManager.getAlphaNumericString(10);
        URLManager.newLink(action, secret);
        return getURL(type, secret, key, action);
    }

    public static String getURL(String type, String secret, String key, String action){
        return Control.serverSettings.URLACTIVELINK() + "/" + Type + type + Secret + secret + Key + key + Action + action + "/ ";
    }


    public static HyperURL ReadURL(String URL){
        try {
            URL = URL.substring(URL.indexOf("/") + 1, URL.lastIndexOf("/"));
            URL = URL.substring(0, URL.lastIndexOf("/"));
            HyperURL hyperURL = new HyperURL();
            hyperURL.Type = URL.substring(URL.indexOf(Type) + Type.length(), URL.indexOf(Secret)).trim();
            hyperURL.SecretKey = URL.substring(URL.indexOf(Secret) + Secret.length(), URL.indexOf(Key)).trim();
            hyperURL.Key = URL.substring(URL.indexOf(Key) + Key.length(), URL.indexOf(Action)).trim();
            hyperURL.Action = URL.substring(URL.indexOf(Action) + Action.length()).trim();
            return hyperURL;
        }catch (Exception e){
            return null;
        }
    }

    public boolean isValid(HyperURL hyperURL){
        if(map.containsKey(hyperURL.Action)){
            return map.get(hyperURL.Action).equals(hyperURL.SecretKey);
        }return false;
    }

}
