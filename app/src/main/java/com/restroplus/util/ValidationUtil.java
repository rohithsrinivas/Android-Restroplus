package com.restroplus.util;

import java.util.List;

/**
 * Created by M1037764 on 12/15/2017.
 */

public class ValidationUtil {

    public static boolean checkForEmptyString (String input){
        if(input != null && !(input.equals("")))
            return true;
        else
            return false;
    }

    public static boolean checkForEmptyListWithSize (List<?> list){
        if(list != null && list.size() > 0){
            return true;
        }
        return false;
    }

    public static boolean checkForEmptyList (List<?> list){
        if(list != null){
            return true;
        }
        return false;
    }

    public static boolean checkListSize(List<?> list){
        if(list!=null && list.size() == 0){
            return true;
        }
        return false;
    }


}
