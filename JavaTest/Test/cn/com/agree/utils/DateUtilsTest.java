package cn.com.agree.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    void parseTimestamp() {
//        String result1 = new DateUtils().parseTimestamp(11);
        String result2 = new DateUtils().parseTimestamp(1581170060333l);
        String result3 = new DateUtils().parseTimestamp(1581170060);
        assert result2.equals("2020-02-08 21:54:20");
        assert result3.equals("2020-02-08 21:54:20");
    }
}