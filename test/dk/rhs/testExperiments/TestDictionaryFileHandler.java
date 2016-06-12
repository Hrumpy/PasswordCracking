/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.rhs.testExperiments;

import dk.rhs.experiments.DictionaryFileHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Standard
 */
public class TestDictionaryFileHandler {

    @Test
    public void testGetPartition() {
        try {
            DictionaryFileHandler dfh = new DictionaryFileHandler("webster-dictionary.txt", 10000000);
            fail("NullPointerException expected");
        } catch (IllegalArgumentException ex) {
        } catch (FileNotFoundException ex) {
            fail("FileNotFoundException");
        } catch (IOException ex) {
            fail("IOException");
        }
        try {
            DictionaryFileHandler dfh = new DictionaryFileHandler("smallDictionary.txt", 4);
            
            assertEquals(39,dfh.getDictionarySize() );
            assertEquals(9,dfh.getPartitionSize());
            assertEquals(27,dfh.getLastPartitionStart());
            
            for (int i = 0; i < 3; i++) {
            String[] partition = dfh.getNextAvailablePartition();
            assertEquals(dfh.getPartitionSize(),partition.length);
            }
            
            String[] lastPartition = dfh.getNextAvailablePartition();
            assertEquals(dfh.getLastPartitionSize(),lastPartition.length);
            

        } catch (FileNotFoundException ex) {
            fail("FileNotFoundException");
        } catch (IOException ex) {
            fail("IOException");
        }
    }
}
