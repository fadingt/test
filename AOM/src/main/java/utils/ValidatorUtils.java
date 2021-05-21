package utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fadingt
 * @time:2016年5月17日
 */

public class ValidatorUtils {
    private static final Pattern CELLPATTERN = Pattern.compile("A-Za-z]+\\d+");
    private static final Pattern NUMPATTERN = Pattern.compile("\\d+");
    private static final Pattern STRPATTERN = Pattern.compile("[A-Za-z]+");

    public static void main(String[] args) {
        System.out.println(isCellString("bA123"));
    }

    public static boolean isCellString(String position) {
        Matcher cellMatcher = CELLPATTERN.matcher(position);
        return cellMatcher.matches();
    }

    public static ArrayList<Integer> numberString(String position) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        Matcher intMatcher = NUMPATTERN.matcher(position);
        while (intMatcher.find()) {
            arrayList.add(Integer.valueOf(intMatcher.group()));
        }
        return arrayList;
    }

    public static ArrayList<String> letterString(String position) {
        ArrayList<String> arrayList = new ArrayList<>();
        Matcher matcher = STRPATTERN.matcher(position);
        while (matcher.find()) {
            arrayList.add(matcher.group());
        }
        return arrayList;
    }

}
