package Appl.WebSever;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.HashMap;

public class URLAction {

    public static HashMap<String, URLAction> hashMap = new HashMap<>();

    public OnNewURL onNewURL;
    public String type;

    public URLAction(String type, OnNewURL onNewURL){
        this.type = type;
        this.onNewURL = onNewURL;
        hashMap.put(type, this);
    }

    public URLManager getURLManager(HyperURL hyperURL){
        return onNewURL.getURLManager(hyperURL);
    }
}
