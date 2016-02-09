package alainvanhout.rest.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class JsonUtils {

    public static String definitionToJson(Map<String, Object> definitionMap) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(definitionMap);
    }
}
