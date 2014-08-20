package com.promotioner.main.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chen on 14-8-16.
 */
public class ValidationUtils {

    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
