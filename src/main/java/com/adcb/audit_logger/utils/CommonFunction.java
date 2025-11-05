package com.adcb.audit_logger.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFunction {

    public static String extractAppRefFromUrl(String path) {
        if (path == null) {
            return null;
        }
        String[] parts = path.split("/");
        for (String part : parts) {
            if (part.startsWith("DIG_")) {
                return part; // return first segment that starts with DIG_
            }
        }
        return null; // not found
    }

    public static String extractAppRefFromPayload(String payload) {
        if (payload == null) {
            return null;
        }
        // Simple regex to find DIG_ followed by word characters or digits
        Pattern pattern = Pattern.compile("DIG_[A-Za-z0-9_]+");
        Matcher matcher = pattern.matcher(payload);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
