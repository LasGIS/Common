import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class HashMapTest definition.
 *
 * @author VLaskin
 * @since 21.05.2023 : 1:14
 */
public class HashMapTest {
    final static String[] UNIQUE_KEY = {
        "LongKey0123456789012345678901234567890123456789DB",
        "LongKey0123456789012345678901234567890123456789Ca",
        "I have a common prefixDB",
        "I have a common prefixCa"
    };

    @Test
    public void hashCodeCollision() {
        int hash0 = UNIQUE_KEY[2].hashCode();
        int hash1 = UNIQUE_KEY[3].hashCode();
        Assertions.assertEquals(hash0, hash1);
        Assertions.assertEquals(UNIQUE_KEY[0].hashCode(), UNIQUE_KEY[1].hashCode());
    }

    @Test
    public void addHashMap() {
        final Map<String, String> map = new HashMap<>();
        String val = map.put(UNIQUE_KEY[0], "val");
        val = map.put(UNIQUE_KEY[0], "val2");
        val = map.put(UNIQUE_KEY[1], "val3");
        val = map.put(UNIQUE_KEY[1], "val4");
    }
}
