package com.example.demo.util;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author Administrator
 */
public class MessageUtil {
    private static final String PLACEHOLDER = "#";
    public static Map<String, String> messageMap = new HashMap();
    static final MessageFormat FORMAT;

    public MessageUtil() {
    }

    public static String getText(String key, String... params) {
        String mes = messageMap.get(key);
        if (params != null && params.length != 0) {
            FORMAT.applyPattern(mes);
            return FORMAT.format(params);
        } else {
            return mes;
        }
    }

    public static String customMessageDefault(String message, String... params) {
        return customMessage(message, "#", params);
    }

    public static String customMessage(String message, String placeholder, String... params) {
        String[] var3 = params;
        int var4 = params.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String param = var3[var5];
            message = StringUtils.replace(message, placeholder, param, 1);
        }

        return message;
    }

    static {
        ResourceBundle msgRes = ResourceBundle.getBundle("biz_message");
        Set<String> keySet = msgRes.keySet();
        Iterator var2 = keySet.iterator();

        while(var2.hasNext()) {
            String key = (String)var2.next();
            messageMap.put(key, msgRes.getString(key));
        }

        FORMAT = new MessageFormat("");
    }
}

