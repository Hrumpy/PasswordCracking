package dk.rhs.distributedpasswordcracking;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andersb
 */
public class PasswordFileHandlerTest {
     

   


    private final String[] usernames = new String[]{"marius", "ocnyan", "dude", "dude_1","dude_2","dude_3"};
    private final String[] passwords = new String[]{"Abantes","Abarambo","Abaris","Aaronite","Aaronitic","A star"};

    @Test
    public void testWriteReadPasswordFile() throws NoSuchAlgorithmException, IOException {

        final String filename = "passwords.txt";

        PasswordFileHandler.writePasswordFile(filename, usernames, passwords);

        final List<UserInfo> userInfos = PasswordFileHandler.readPasswordFile(filename);
        assertEquals(usernames.length, userInfos.size());
        final UserInfo userInfo = userInfos.get(0);
        assertEquals("anders", userInfo.getUsername());
        final MessageDigest messageDigest = MessageDigest.getInstance(PasswordFileHandler.MESSAGE_DIGEST_ALGORITHM);
        final byte[] encryptedPassword = messageDigest.digest(passwords[0].getBytes());
        assertArrayEquals(encryptedPassword, userInfo.getEntryptedPassword());

        for (String username : usernames) {
            assertTrue(userInfos.contains(new UserInfo(username, "not important")));
        }
    }

    @Test
    public void testWritePasswordFile() throws Exception {
        try {
            PasswordFileHandler.writePasswordFile(null, usernames, passwords);
            fail("NullPointerException expected");
        } catch (NullPointerException ex) {
        }
    }

    @Test
    public void testReadPasswordFile() throws IOException {
        try {
            PasswordFileHandler.readPasswordFile("doesNotExist.txt");
            fail();
        } catch (FileNotFoundException ex) {
        }
    }
}
