package domain.ngoss;

import org.junit.jupiter.api.Test;


import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 5/21/2021 1:55 PM
 */
class TSnapContractsyncEntityTest {
    @Test void dateTest(){
        TSnapContractsyncEntity entity = new TSnapContractsyncEntity();
        entity.setsBackdate(new Date(1535644800000L));
        System.out.println(entity.getsBackdate());
    }
}