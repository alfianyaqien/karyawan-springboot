package com.alfian.test.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class GenericRs {

    public Map templateSuccess(Object o){
        Map map = new HashMap();
        map.put("data", o);
        map.put("message", "Success");
        map.put("status", "200");
        return map;
    }
    public Map templateError(Object o, String status){
        Map map = new HashMap();
        map.put("message", o);
        map.put("status", status);
        return map;
    }
    public Map notFound(Object o){
        Map map = new HashMap();
        map.put("message", o);
        map.put("status", "404");
        return map;
    }
    public boolean chekNull(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }



    public boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }
}
