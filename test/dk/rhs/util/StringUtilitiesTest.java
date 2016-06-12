package dk.rhs.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andersb
 */
public class StringUtilitiesTest {

    @Test
    public void testCapitalize() {
        assertEquals("Anders", StringUtilities.capitalize("anders"));
        assertEquals("Anders", StringUtilities.capitalize("Anders"));
        assertEquals("A", StringUtilities.capitalize("a"));
        assertEquals("", StringUtilities.capitalize(""));
        try {
            assertNull(StringUtilities.capitalize(null));
            fail();
        } catch (IllegalArgumentException ex) {
            assertEquals("str is null", ex.getMessage());
        }
    }
}
