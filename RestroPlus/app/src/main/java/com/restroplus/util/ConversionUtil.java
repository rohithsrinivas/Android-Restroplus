package com.restroplus.util;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by M1037764 on 12/16/2017.
 */

public class ConversionUtil {

    public static Gson gson = new Gson();

    public static Integer convertStringToInteger(String input){
        try {
            return Integer.parseInt(input);
        }
        catch(Exception e){
            return 0;
        }
    }

    public static String convertToJsonString(Object input){
        return gson.toJson(input);
    }

    public static String convertListToJsonString(List input){
        return gson.toJson(input);
    }

}
