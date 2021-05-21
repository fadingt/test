package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtils {

    public static void main(String[] args) {
        System.out.println(new Date(1535644800000L));
/*        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        for (String item:list) {
            if("2".equals(item)){
                list.remove(item);
            }
        }
        System.out.println(list.get(0));*/

//        System.out.println(new DateUtils().parseTimestamp(1581170060333l));
    }
    public String parseTimestamp(long timestamp) {
        return parseTimestamp(timestamp, "YYYY-MM-dd HH:mm:ss");
    }

    public String parseTimestamp(long timestamp, String pattern) {
        String result;
        if ((timestamp + "").length() == 10) {
            timestamp = Long.parseLong(timestamp + "000");
        } else if ((timestamp + "").length() != 13) {
            throw new IllegalArgumentException("timestamp length illegal");
        }
        Date date = new Date(timestamp);
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        System.out.println(dateFormat.format(date));
        result = dateFormat.format(date);
        return result;
    }
}
