package Utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Bundle {


    public static short[] readSrtArrayNode(JsonNode node){
        return new short[]{node.path("X").shortValue(), node.path("Y").shortValue(), node.path("W").shortValue(), node.path("H").shortValue()};
    }

    public static HashMap<String, Object> bundleShortArray(short[] array){
        HashMap<String, Object> map = new HashMap<>();
        map.put("X", array[0]);
        map.put("Y", array[1]);
        map.put("W", array[2]);
        map.put("H", array[3]);
        return map;
    }

    public static ArrayList<String> readStringToStringArray(String string){
            if(!string.equals("null")) {
                string = string.replaceAll("\\[", "").replaceAll("]", "").replaceAll("^\"+|\"+$", "");
                if(!string.isEmpty()) {
                    String[] array = string.split(",");
                    return new ArrayList<>(Arrays.asList(array));
                }return new ArrayList<>();
            }else{
                return new ArrayList<>();
            }
    }

    public static float[] parseFloatArray(String string, int size){
        ArrayList<String> list = readStringToStringArray(string);
        float[] array = new float[size];
        if(string.equals("null"))return null;
        for(int i = 0; i < list.size(); ++i){
            if(list.get(i).isEmpty())return null;
            array[i] = Float.parseFloat(list.get(i));
        }
        return array;
    }



}
