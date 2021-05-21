package domain;

import domain.paasaom.Dictionary;
import org.junit.jupiter.api.Test;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 11/23/2020 1:10 PM
 */
class DictionaryTest {

    @Test
    void translatedict() {
        assert Dictionary.translatedict("trfl",null) == null;
        assert "否".equals(Dictionary.translatedict("trfl","1"));
        assert "是".equals(Dictionary.translatedict("trfl","2"));
    }
}