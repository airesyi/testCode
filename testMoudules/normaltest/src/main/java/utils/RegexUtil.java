package utils;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static boolean isMatch(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        boolean rs = matcher.matches();
        return rs;
    }

    public static boolean isEmail(String str) {
        String regex = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        boolean rs = matcher.matches();
        return rs;
    }

    public static String urlToUri(String str) {
        String regex = "([A-Za-z]+://)?(.*?)(/.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(matcher.groupCount());
        }
        return null;
    }

    @Test
    public void test2() {
        String url = "172.16.10.145/group1/M00/00/00/rBAKkVsXrsyAVG5AAAZIEoVhbfM199.jpg_200x200.jpg";
        String uri = urlToUri(url);
        System.out.println(uri);
    }
}
