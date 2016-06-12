package dk.rhs.distributedpasswordcracking;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andersb
 * 
 */
public class CrackerCentralizedTest {

    @Test
    public void testCheckWordWithVariations() throws NoSuchAlgorithmException, IOException {
        final List<UserInfo> passwords = new ArrayList<UserInfo>();
        final String dictionaryEntry = "secret";
        List<UserInfoClearText> crackedUsers = CrackerCentralized.checkWordWithVariations(dictionaryEntry, passwords);
        assertTrue(crackedUsers.isEmpty());

        final EncrypterEncoder encrypterEncoder = new EncrypterEncoder("SHA");

        final UserInfo userInfo = new UserInfo("anders", encrypterEncoder.encryptAndEncode("secret"));
        passwords.add(userInfo);
        crackedUsers = CrackerCentralized.checkWordWithVariations(dictionaryEntry, passwords);
        assertEquals(1, crackedUsers.size());
        assertEquals("anders", crackedUsers.get(0).getUsername());

        final UserInfo userInfo2 = new UserInfo("peter", encrypterEncoder.encryptAndEncode("secret"));
        passwords.add(userInfo2);
        crackedUsers = CrackerCentralized.checkWordWithVariations(dictionaryEntry, passwords);
        assertEquals(2, crackedUsers.size());
        assertEquals("anders", crackedUsers.get(0).getUsername());
        assertEquals("peter", crackedUsers.get(1).getUsername());
    }

    @Test
    public void testCheckSingleWord() throws NoSuchAlgorithmException, IOException {
        final List<UserInfo> passwords = new ArrayList<UserInfo>();
        final String dictionaryEntry = "secret";
        List<UserInfoClearText> crackedUsers = CrackerCentralized.checkSingleWord(passwords, dictionaryEntry);
        assertTrue(crackedUsers.isEmpty());

        final EncrypterEncoder encrypterEncoder = new EncrypterEncoder("SHA");

        final UserInfo userInfo = new UserInfo("anders", encrypterEncoder.encryptAndEncode("secret"));
        passwords.add(userInfo);
        crackedUsers = CrackerCentralized.checkSingleWord(passwords, dictionaryEntry);
        assertEquals(1, crackedUsers.size());
        assertEquals("anders", crackedUsers.get(0).getUsername());

        final UserInfo userInfo2 = new UserInfo("peter", encrypterEncoder.encryptAndEncode("secret"));
        passwords.add(userInfo2);
        crackedUsers = CrackerCentralized.checkSingleWord(passwords, dictionaryEntry);
        assertEquals(2, crackedUsers.size());
        assertEquals("anders", crackedUsers.get(0).getUsername());
        assertEquals("peter", crackedUsers.get(1).getUsername());
    }

}
