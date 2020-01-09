package utils;

public class StringUtils {
    public static String rpad(String str, int len, String padstr) {
        StringBuilder result = new StringBuilder();
        result.append(str);
        for (int i = 0; i < len; i++) {
            result.append(padstr);
        }
        return result.toString();
    }

    public static String lpad(String str, int len, String padstr) {
        StringBuilder result = new StringBuilder();
        String prefix = new String(padstr);
        for (int i = 0; i < len; i++) {
            result.append(prefix);
        }
        result.append(str);
        return result.toString();
    }
}
